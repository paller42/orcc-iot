/*
 * Copyright (c) 2021 Gabor Paller
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

#ifndef NATIVE_ACTORS_H
#define NATIVE_ACTORS_H

#include <types.h>

/* These functions are expected to be invoked in specified sequence. read_temp() lazily initializes the I2C subsystem
 * and fetches the calibration parameters. Read_press() and co. only read data prepared by read_temp() */
extern i32 BME280_native_read_temp();
extern i32 BME280_native_read_press();
extern i32 BME280_native_read_hum();

extern u32 BME280_native_get_dig_T1();
extern i32 BME280_native_get_dig_T2();
extern i32 BME280_native_get_dig_T3();
extern u32 BME280_native_get_dig_P1();
extern i32 BME280_native_get_dig_P2();
extern i32 BME280_native_get_dig_P3();
extern i32 BME280_native_get_dig_P4();
extern i32 BME280_native_get_dig_P5();
extern i32 BME280_native_get_dig_P6();
extern i32 BME280_native_get_dig_P7();
extern i32 BME280_native_get_dig_P8();
extern i32 BME280_native_get_dig_P9();
extern u32 BME280_native_get_dig_H1();
extern i32 BME280_native_get_dig_H2();
extern u32 BME280_native_get_dig_H3();
extern i32 BME280_native_get_dig_H4();
extern i32 BME280_native_get_dig_H5();
extern i32 BME280_native_get_dig_H6();
extern void FW_native_delay( i32 delay );

#endif
