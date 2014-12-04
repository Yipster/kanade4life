// Version 1

#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>
#include <math.h>
#include <fstream>
#include <iostream>
#include <ctime>

struct WAV_HEADER
{
	// Header 1
	// RIFF chunk descriptor
	char RIFF[4];
	uint32_t chunk_size;
	char WAVE[4];
	
	// Header 2
	// fmt sub-chunk
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
	
	// Data
	// data sub-chunk
	uint32_t subchunk2_id;
	uint32_t subchunk2_size;
} __attribute__((packed));

int main(int argc, char **argv) {
	clock_t start = clock();
	
	printf("Size of WAV_HEADER: %d\n", sizeof(WAV_HEADER));
	
	struct WAV_HEADER input_header;
	struct WAV_HEADER ir_header;
	
	// Checks to see if the correct number of arguments is passed in
	if (argc != 4) {
		printf("Incorrect number of arguments\n");
		return -1;
	}
	
	FILE *input_file;
	FILE *ir_file;
	FILE *output_file;
	
	input_file = fopen(argv[1], "rb");
	ir_file = fopen(argv[2], "rb");
	output_file = fopen(argv[3], "wb");
	
	// Check to see if files are null
	if (input_file == NULL || ir_file == NULL || output_file == NULL) {
		printf("Cannot open the file.\n");
		return -1;
	}
	
	// headers of input and IR sound files
	fread(&input_header, sizeof(WAV_HEADER), 1, input_file);
	fread(&ir_header, sizeof(WAV_HEADER), 1, ir_file);
	
	printf("Input file: \n");
	printf("format: %i, channels: %i, sample rate: %d\n", input_header.audio_format, input_header.num_channels, input_header.sample_rate);
	printf("size: %d, bits per sample: %i\n", input_header.subchunk2_size, input_header.bits_per_sample);
	
	printf("IR file: \n");
	printf("format: %i, channels: %i, sample rate: %d\n", ir_header.audio_format, ir_header.num_channels, ir_header.sample_rate);
	printf("size: %d, bits per sample: %i\n", ir_header.subchunk2_size, ir_header.bits_per_sample);
	
	if (input_header.num_channels > 2 || ir_header.num_channels > 2) {
		printf("Unsupported channel number");
		return -1;
	}
	
	size_t input_bytes_per_sample = input_header.bits_per_sample / 8;
	size_t N = input_header.subchunk2_size / (input_bytes_per_sample * input_header.num_channels);
	
	size_t ir_bytes_per_sample = ir_header.bits_per_sample / 8;
	size_t M = ir_header.subchunk2_size / (ir_bytes_per_sample * ir_header.num_channels);
	
	printf("N = %d\n", N);
	printf("M = %d\n", M);
	
	// Initialize buffers for input and IR
	double *input_buf1 = new double[N];
	double *input_buf2 = new double[N];
	double *ir_buf1 = new double[M];
	double *ir_buf2 = new double[M];
	
	// Read input data
	printf("Reading input data... ");
	if (input_bytes_per_sample == 1) {
		double sample;
		for (int i = 0; i < N; i++) {
			fread(&sample, input_bytes_per_sample, 1, input_file);
			printf("Reading into input_buf: %d/%d	%d\n", i, N, sample);
			input_buf1[i] = sample;
			if (input_header.num_channels == 2) {
				fread(&sample, input_bytes_per_sample, 1, input_file);
				input_buf2[i] = sample;
			}
		}
	}
	else if (input_bytes_per_sample == 2) {
		double sample;
		for (int i = 0; i < N; i++) {
			fread(&sample, input_bytes_per_sample, 1, input_file);
			printf("Reading into input_buf: %d/%d	%d\n", i, N, sample);
			input_buf1[i] = sample;
			if (input_header.num_channels == 2) {
				fread(&sample, input_bytes_per_sample, 1, input_file);
				input_buf2[i] = sample;
			}
		}
	}
	else {
		printf("Unsupported sample size %d\n", input_bytes_per_sample);
		return -1;
	}
	printf("complete\n");
	
	// Read IR data
	printf("Reading IR data... ");
	if (ir_bytes_per_sample == 1) {
		double sample;
		for (int i = 0; i < M; i++) {
			//printf("Reading into ir_buf: %d/%d\n", i, M);
			fread(&sample, ir_bytes_per_sample, 1, ir_file);
			ir_buf1[i] = sample;
			if (ir_header.num_channels == 2) {
				fread(&sample, ir_bytes_per_sample, 1, ir_file);
				ir_buf2[i] = sample;
			}
		}
	}
	else if (ir_bytes_per_sample == 2) {
		double sample;
		for (int i = 0; i < M; i++) {
			//printf("Reading into ir_buf: %d/%d\n", i, M);
			fread(&sample, ir_bytes_per_sample, 1, ir_file);
			ir_buf1[i] = sample;
			if (ir_header.num_channels == 2) {
				fread(&sample, ir_bytes_per_sample, 1, ir_file);
				ir_buf2[i] = sample;
			}
		}
	}
	else {
		printf("Unsupported sample size %d\n", ir_bytes_per_sample);
		return -1;
	}
	printf("complete\n");
	
	// Convolve
	printf("Convolving... ");
	// Calculate the sample size of output buffer
	int P = N + M - 1;
	double *output_buf1 = new double[P];
	double *output_buf2 = new double[P];
	
	for (int p = 0; p < P; p++) {
		//printf("Initializing output_buf[%d]\n", p);
		output_buf1[p] = 0;
		output_buf2[p] = 0;
	}
	
	for (int n = 0; n < N; n++) {
		//printf("Convolving: %d/%d\n", n, N);
		for (int m = 0; m < M; m++) {
			output_buf1[n+m] += ir_buf1[m] * input_buf1[n];
			output_buf2[n+m] += ir_buf2[m] * input_buf2[n];
		}
	}
	printf("complete\n");
	
	// Write data
	printf("Writing data... ");
	fwrite(&input_header, sizeof(WAV_HEADER), 1, output_file);
	if (input_bytes_per_sample == 1) {
		double sample;
		for (int i = 0; i < P; i++) {
			//printf("Writing into output_file: %d/%d\n", i, P);
			sample = output_buf1[i];
			fwrite(&sample, input_bytes_per_sample, 1, output_file);
			if (input_header.num_channels == 2) {
				sample = output_buf2[i];
				fwrite(&sample, input_bytes_per_sample, 1, output_file);
			}
		}
	}
	else if (input_bytes_per_sample == 2) {
		double sample;
		for (int i = 0; i < P; i++) {
			//printf("Writing into output_file: %d/%d\n", i, P);
			sample = output_buf1[i];
			fwrite(&sample, input_bytes_per_sample, 1, output_file);
			if (input_header.num_channels == 2) {
				sample = output_buf2[i];
				fwrite(&sample, input_bytes_per_sample, 1, output_file);
			}
		}
	}
	else {
		printf("Unsupported sample size %d\n", input_bytes_per_sample);
		return -1;
	}
	printf("complete\n");
	
	// Close files
	fclose(input_file);
	fclose(ir_file);
	fclose(output_file);
	
	printf("done\n");
	clock_t end = clock();
	clock_t final = (end - start)/CLOCKS_PER_SEC;
	printf("Run time: %d\n", final);
	
	return 0;
}
