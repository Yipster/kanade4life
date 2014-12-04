/*
* Name: Brandon Yip
* CPSC 501 Assignment 4
* 
* This is the optimized program that will do the convolution process using FFT convolution.
* 1) Takes two inputs, one which is the dry sound .wav file and one which is the impulse sound .wav file
* 2) Reads both inputs
* 3) Convolves the two inputs into an output buffer
* 4) Writes output buffer to output file
* 5) Takes the total duration of the process.
*/


#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>
#include <iostream>
#include <ctime>
#include <math.h>
#include <fstream>
#define TWO_PI (2.0 * M_PI)
// PI is defined in math.h with the use of M_PI

//This is the wave header
struct WAVE_HEADER
{
	// Header 1 RIFF chunk descriptor
	char RIFF[4];
	uint32_t chunk_size;
	char WAVE[4];
	
	// Header 2
	char subchunk1_id[4];
	uint32_t subchunk1_size;
	uint16_t audio_format;
	uint16_t num_channels;
	uint32_t sample_rate;
	uint32_t byte_rate;
	uint16_t block_align;
	uint16_t bits_per_sample;
	
	// Unused
	uint16_t unused_bytes;
	
	// Data data sub-chunk
	uint32_t subchunk2_id;
	uint32_t subchunk2_size;
} __attribute__((packed));



//method to calculate dft
void dft(double x[], int N, double a[], double b[]) {
	int n, k;
	double omega = TWO_PI/ (double) N;
	for(k = 0; k < N; k++) {
		a[k] = b[k] = 0.0;
		for(n = 0; n < N; n++) {
			a[k] += (x[n] * cos(omega * n * k));
			b[k] -= (x[n] *  sin(omega * n * k));
		}
	}
}




// main program
int main(int argc, char **argv) {
	clock_t starttime = clock();
	
	printf("Size of WAVE_HEADER: %d\n", sizeof(WAVE_HEADER));
	
	//make two structs, for each input
	struct WAVE_HEADER input_header;
	struct WAVE_HEADER impulse_header;
	
	// Checks to see if the correct number of arguments is passed in
	if (argc != 4) {
		printf("Incorrect number of arguments\n");
		printf("Usage: <program> <inputfile> <impulsefile> <outputfile>");
		return -1;
	}
	
	//create new files
	FILE *input_file;
	FILE *impulse_file;
	FILE *output_file;
	
	//open files, with input and impulse being read and output being write
	input_file = fopen(argv[1], "rb");
	impulse_file = fopen(argv[2], "rb");
	output_file = fopen(argv[3], "wb");
	
	// Check to see if any of the files are null
	if (input_file == NULL) {
		printf("Cannot open input file.\n");
		return -1;
	}
	if (impulse_file == NULL) {
		printf("Cannot open impulse file. \n");
		return -1;
	}
	if (output_file == NULL) {
		printf("Cannot open output file. \n");
	}

	
	// information (header) of input file
	fread(&input_header, sizeof(WAVE_HEADER), 1, input_file);
	
	printf("==================================================\n");
	printf("Input file: \n");
	printf("format: %i, channels: %i, sample rate: %d, ", input_header.audio_format, input_header.num_channels, input_header.sample_rate);
	printf("size: %d, bits per sample: %i\n", input_header.subchunk2_size, input_header.bits_per_sample);
	printf("==================================================\n");	


	// information (header) of impulse file (ir)
	fread(&impulse_header, sizeof(WAVE_HEADER), 1, impulse_file);
	printf("==================================================\n");	
	printf("IR file: \n");
	printf("format: %i, channels: %i, sample rate: %d, ", impulse_header.audio_format, impulse_header.num_channels, impulse_header.sample_rate);
	printf("size: %d, bits per sample: %i\n", impulse_header.subchunk2_size, impulse_header.bits_per_sample);
	printf("==================================================\n");	

	//Checks the channel numbers to see if they are appropriate for both files
	if (input_header.num_channels > 2 || impulse_header.num_channels > 2) {
		printf("Unsupported channel number");
		return -1;
	}
	
	//takes bits per sample and divides by 8 to get bytes per sample
	size_t input_bps = input_header.bits_per_sample / 8;
	//use bytes per sample and multiply with channels and use the result to divide data size to get N
	size_t N = input_header.subchunk2_size / (input_bps * input_header.num_channels);
	
	//takes bits per sample and divides by 8 to get bytes per sample
	size_t impulse_bps = impulse_header.bits_per_sample / 8;
	//use bytes per sample and multiply with channels and use the result to divide data size to get N
	size_t M = impulse_header.subchunk2_size / (impulse_bps * impulse_header.num_channels);
	
	//prints the values of N and M
	printf("Values: \n");
	printf("Size of input (N) = %d\n", N);
	printf("Size of impulse (M) = %d\n", M);
	
	size_t A = N + M - 1;
	if((A % 2) == 1)
		A++;
		
	
	// Initialize buffers for input and IR
	double *input_buffer1 = new double[A];
	double *input_buffer2 = new double[A];
	double *impulse_buffer1 = new double[A];
	double *impulse_buffer2 = new double[A];
	
	// Read input data
	printf("Reading input data... ");
	if (input_bps == 1) {
		double sample;
		for (int i = 0; i < N; i++) {
			fread(&sample, input_bps, 1, input_file);
			//printf("Reading into input_buffer: %d/%d	%d\n", i, N, sample);
			input_buffer1[i] = sample;
			if (input_header.num_channels == 2) {
				fread(&sample, input_bps, 1, input_file);
				input_buffer2[i] = sample;
			}
		}
		for(int i = N; i < A; i++) {
			input_buffer1[i] = 0.0;
			input_buffer2[i] = 0.0;
		}
	}
	else if (input_bps == 2) {
		double sample;
		for (int i = 0; i < N; i++) {
			fread(&sample, input_bps, 1, input_file);
			//printf("Reading into input_buffer: %d/%d	%d\n", i, N, sample);
			input_buffer1[i] = sample;
			if (input_header.num_channels == 2) {
				fread(&sample, input_bps, 1, input_file);
				input_buffer2[i] = sample;
			}
		}
		for(int i = N; i < A; i++) {
			input_buffer1[i] = 0.0;
			input_buffer2[i] = 0.0;
		}
	}
	else {
		printf("Unsupported sample size %d\n", input_bps);
		return -1;
	}
	printf("Completed Reading of Input data\n");
	
	
	// Read IR data
	printf("Reading IR data... \n");
	if (impulse_bps == 1) {
		double sample;
		for (int i = 0; i < M; i++) {
			//printf("Reading into impulse_buffer: %d/%d\n", i, M);
			fread(&sample, impulse_bps, 1, impulse_file);
			impulse_buffer1[i] = sample;
			if (impulse_header.num_channels == 2) {
				fread(&sample, impulse_bps, 1, impulse_file);
				impulse_buffer2[i] = sample;
			}
		}
		for(int i = M; i < A; i++) {
			impulse_buffer1[i] = 0.0;
			impulse_buffer2[i] = 0.0;
		}
	}
	else if (impulse_bps == 2) {
		double sample;
		for (int i = 0; i < M; i++) {
			//printf("Reading into impulse_buffer: %d/%d\n", i, M);
			fread(&sample, impulse_bps, 1, impulse_file);
			impulse_buffer1[i] = sample;
			if (impulse_header.num_channels == 2) {
				fread(&sample, impulse_bps, 1, impulse_file);
				impulse_buffer2[i] = sample;
			}
		}
		for(int i = M; i < A; i++) {
			impulse_buffer1[i] = 0.0;
			impulse_buffer2[i] = 0.0;
		}
	}
	else {
		printf("Unsupported sample size %d\n", impulse_bps);
		return -1;
	}
	printf("Completed Reading of IR data\n");
	
	
	
	size_t B = A/2;
	//Changes start now
	//First to convert both input and impulse to the frequency domain
	double *real_input = new double[B];
	double *imaginary_input = new double[B];
	double *real_impulse = new double[B];
	double *imaginary_impulse = new double[B];
	//Convert input where input_buffer is x[n]
	if(input_bps == 1 | input_bps == 2) {
		int n, k;
		double omega = (M_PI *2)/(double) N;
		printf("Value of omega: %d\n", omega);
		for(k = 0; k < B; k++){
			real_input[k] = 0.0;
			imaginary_input[k] = 0.0;
			//printf("%d\t\t%zu\n%",k, B);
			for(n = 0; n < B; n++){
				real_input[k] += (input_buffer1[n] * cos(omega * n * k));
				imaginary_input[k] -= (input_buffer1[n] * sin(omega * n * k));
			}
		}
	}
	printf("Completed change of input to frequency domain");
	
	//convert impulse where impulse_buffer is h[n]
	if(impulse_bps == 1 | impulse_bps == 2) {
		int n, k;
		double omega =(M_PI *2)/(double) N;
		printf("Value of omega: %d\n", omega);
		for(k = 0; k < B; k++){
			real_impulse[k] = imaginary_impulse[k] = 0.0;
			for(n = 0; n < B; n++){
				real_impulse[k] += (impulse_buffer1[n] * cos(omega * n * k));
				imaginary_impulse[k] -= (impulse_buffer1[n] * sin(omega * n * k));
			}
		}
	}
	printf("Completed change of impulse to frequency domain");
		
	//Initialize output buffer
	printf(" Starting Convolve Process... \n");
	// Calculate the sample size of output buffer
	//Generate new buffers for output
	double *output_buffer1 = new double[A];
	double *output_buffer2 = new double[A];
	
	//initialize the output buffer
	for (int p = 0; p < A; p++) {
		//printf("Initializing output_buffer[%d]\n", p);
		output_buffer1[A] = 0;
		output_buffer2[A] = 0;
	}
	printf(" Initialization of output buffer complete\n");
	
	
	
	
	// Convolve Process Start
	printf(" Starting Convolving Process... \n");
	//start convolve process by multiplying impulse and input buffer and placing it in n+m slot in output buffer
	for (int n = 0; n < B; n++) {
		//printf("Convolving: %d/%d\n", n, N);
		output_buffer1[n] += (real_input[n] * real_impulse[n]) -(imaginary_input[n] * imaginary_impulse[n]);
		output_buffer2[n] += (imaginary_input[n] * imaginary_impulse[n]) + (real_input[n] * real_impulse[n]);
	}
	printf("Completed Convolve Process\n");
	
	
	
	
	// Write data to output file
	printf("Starting to Write data... ");
	fwrite(&input_header, sizeof(WAVE_HEADER), 1, output_file);
	if (input_bps == 1) {
		double sample;
		for (int i = 0; i < A; i++) {
			//printf("Writing into output_file: %d/%d\n", i, P);
			sample = output_buffer1[i];
			fwrite(&sample, input_bps, 1, output_file);
			if (input_header.num_channels == 2) {
				sample = output_buffer2[i];
				fwrite(&sample, input_bps, 1, output_file);
			}
		}
	}
	else if (input_bps == 2) {
		double sample;
		for (int i = 0; i < A; i++) {
			//printf("Writing into output_file: %d/%d\n", i, P);
			sample = output_buffer1[i];
			fwrite(&sample, input_bps, 1, output_file);
			if (input_header.num_channels == 2) {
				sample = output_buffer2[i];
				fwrite(&sample, input_bps, 1, output_file);
			}
		}
	}
	else {
		printf("Unsupported sample size %d\n", input_bps);
		return -1;
	}
	printf("Completed Writing Process\n");
	
	// Close all files
	fclose(input_file);
	fclose(impulse_file);
	fclose(output_file);
	
	//Find the duration of the convolving process
	printf("done\n");
	clock_t endtime = clock();
	clock_t duration = (endtime - starttime)/CLOCKS_PER_SEC;
	printf("Total Runtime: %d seconds.\n", duration);
	
	
	return 0;
}
