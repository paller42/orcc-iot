/*
* Native actor functions to be used in the weather pole model. These functions mainly communicate with the BME280 sensor
*/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "hwsupport.h"
#include "i2c.h"
#include "native-actors.h"

// 0x76 << 1, as required by STM32F407 addressing logic (bit 0 is reserved for the R/W indicator)
#define BME280_ADDR	0xEC

#define BME280_REG_ID	0xD0
#define BME280_REG_CALIB_BASE_1	0x88
#define BME280_REG_CALIB_BASE_2	0xE1
#define BME280_REG_CTRL_HUM	0xF2
#define BME280_REG_CTRL_MEAS	0xF4
#define BME280_REG_CONFIG	0xF5
#define BME280_REG_MEAS_BASE	0xF7
#define BME280_REG_CTRL_HUM	0xF2
#define BME280_REG_CTRL_MEAS	0xF4
#define BME280_REG_CONFIG	0xF5

struct bme280_calib_struct {
	unsigned int	dig_T1;
	int		dig_T2,dig_T3;
	unsigned int	dig_P1;
	int		dig_P2;
	int		dig_P3;
	int		dig_P4;
	int		dig_P5;
	int		dig_P6;
	int		dig_P7;
	int		dig_P8;
	int		dig_P9;
	unsigned int	dig_H1;
	int		dig_H2;
	unsigned int	dig_H3;
	int		dig_H4;
	int		dig_H5;
	int		dig_H6;
	int32_t t_fine;
};
typedef struct bme280_calib_struct bme280_calib;

static void dump_array( uint8_t array[], int array_len ) {
	char line[80],digs[3];
	int col = 0;
	
	line[0] = 0;
	for( int i = 0 ; i < array_len ; ++i ) {
		sprintf( digs,"%02X",array[i] );
		if( col > 0 )
			strcat( line," " );
		strcat( line,digs );
		++col;
		if( col >= 8 ) {
			printf( "%s\xD\xA",line );
			line[0] = 0;
			col = 0;
		}
	}
	if( col > 0 );
		printf( "%s\xD\xA",line );
}

static unsigned int bme280_get_unsigned_char( uint8_t m_buffer[], int pos) {
	unsigned int v;
	v = (unsigned int)m_buffer[pos];
	return v;  
}

static unsigned int bme280_get_unsigned_short( uint8_t m_buffer[], int pos) {
	unsigned int v;
	v = (unsigned int)m_buffer[pos] | (((unsigned int)m_buffer[pos+1]) << 8 );
	return v;  
}

static int bme280_get_signed_char( uint8_t m_buffer[], int pos ) {
	int v;
	v = (unsigned int)m_buffer[pos];
	if( v > 0x7F )
		v -= 128;
	return v;
}

static int bme280_get_signed_short( uint8_t m_buffer[], int pos ) {
	int v;
	v = (unsigned int)m_buffer[pos] | (((unsigned int)m_buffer[pos+1]) << 8 );
	if( v > 0x7FFF )
		v -= 65536;
	return v;
}

static void parse_bme280_calib_data( bme280_calib *c, uint8_t m_buffer[]  ) {
	c->dig_T1 = bme280_get_unsigned_short( m_buffer, 1 );
	c->dig_T2 = bme280_get_signed_short( m_buffer, 3 );
	c->dig_T3 = bme280_get_signed_short( m_buffer, 5 );
	c->dig_P1 = bme280_get_unsigned_short( m_buffer, 7 );
	c->dig_P2 = bme280_get_signed_short( m_buffer, 9 );
	c->dig_P3 = bme280_get_signed_short( m_buffer, 11 );
	c->dig_P4 = bme280_get_signed_short( m_buffer, 13 );
	c->dig_P5 = bme280_get_signed_short( m_buffer, 15 );
	c->dig_P6 = bme280_get_signed_short( m_buffer, 17 );
	c->dig_P7 = bme280_get_signed_short( m_buffer, 19 );
	c->dig_P8 = bme280_get_signed_short( m_buffer, 21 );
	c->dig_P9 = bme280_get_signed_short( m_buffer, 23 );
	c->dig_H1 = bme280_get_unsigned_char( m_buffer, 26 );	// A1
	c->dig_H2 = bme280_get_signed_short( m_buffer, 27 );	// E1
    c->dig_H3 = bme280_get_unsigned_char( m_buffer, 29 );	// E3
// 30->E4, 31->E5, 32->E6
    c->dig_H4 = (int32_t)( ( ((uint32_t)m_buffer[30]) << 4 ) | ( ((uint32_t)m_buffer[31]) & 0xF ) );
    c->dig_H4 &= 0xFFF;
    if( c->dig_H4 > 0x7FF )
		c->dig_H4 -= 0x800;
    c->dig_H5 = (int32_t)( ( ((uint32_t)m_buffer[32]) << 4 ) | ( ( ((uint32_t)m_buffer[31]) & 0xF0 ) >> 4 ) );
    c->dig_H5 = ( c->dig_H5 >> 4 ) & 0xFFFFFF;
    if( c->dig_H5 > 0x7FFFFF )
		c->dig_H5 -= 0x800000;
    c->dig_H6 = bme280_get_signed_char( m_buffer, 33 );	// E7
}

static int get_calib_data( uint8_t m_buffer[] ) {
	printf( "get_calib_data1\xD\xA" );
	if( !I2C1_getregisters( BME280_ADDR, BME280_REG_ID, 1, &m_buffer[0] ) )
		return 0;
	printf( "get_calib_data2\xD\xA" );
	if( !I2C1_getregisters( BME280_ADDR, BME280_REG_CALIB_BASE_1, 26, &m_buffer[1] ) )
		return 0;
	printf( "get_calib_data3\xD\xA" );
	return I2C1_getregisters( BME280_ADDR, BME280_REG_CALIB_BASE_2, 7, &m_buffer[27] );
}

static int get_measurement_data( uint8_t m_meas[] ) {
// t_standby=0, IIR off, SPI disable
	if( !I2C1_writeregister( BME280_ADDR, BME280_REG_CONFIG, 0x00 ) ) 
		return 0;
// humidity 1x oversampling
	if( !I2C1_writeregister( BME280_ADDR, BME280_REG_CTRL_HUM, 0x01 ) ) 
		return 0;
// pressure, temperature: 1x oversampling, forced mode
	if( !I2C1_writeregister( BME280_ADDR, BME280_REG_CTRL_MEAS, 0x25 ) )
		return 0;
	return I2C1_getregisters(  BME280_ADDR, BME280_REG_MEAS_BASE, 8, m_meas );
}

static int initialized = 0;
static uint8_t calib_buffer[34];
static uint8_t meas_buffer[8];
static bme280_calib calib;

i32 BME280_native_read_temp() {
	int32_t base;
	
    if( !initialized ) {
		I2C1_Configuration();
		// Can't leave this loop until calib data is obtained - no point of running the dataflow without it
		printf( "Getting BME280 calibration data ...\xD\xA" );
        while( 1 ) {
			if( get_calib_data( calib_buffer ) )
				break;
			printf( "Calibration data read error\xD\xA" );
			base = TIM2_base();
			while( TIM2_diff( base ) < 2 );
		}
		printf( "Calibration data read\xD\xA" );
		dump_array( calib_buffer, 34 );
		parse_bme280_calib_data( &calib, calib_buffer );
		printf( "BME280 successfully initialized ...\xD\xA" );
		initialized = 1;
    }
	printf( "Getting BME280 measurement data ...\xD\xA" );
    while( 1 ) {
// Also, no point of leaving this loop if we don't have BME280 measurement data (the dataflow has no other input)
		if( get_measurement_data( meas_buffer ) )
			break;
		base = TIM2_base();
		while( TIM2_diff( base ) < 2 );
	}
	printf( "BME280 measurement data read ...\xD\xA" );
	int32_t raw_temperature = 
							(int32_t)( (((uint32_t)meas_buffer[3]) << 12 ) | 
										(((uint32_t)meas_buffer[4]) << 4 ) |
										(((uint32_t)meas_buffer[5]) >> 4 ) );
	return raw_temperature;
}

i32 BME280_native_read_press() {
// It is expected that read_temp has already fetched the measurement data
	int32_t raw_pressure = (int32_t)( (((uint32_t)meas_buffer[0]) << 12 ) | 
										(((uint32_t)meas_buffer[1]) << 4 ) |
										(((uint32_t)meas_buffer[2]) >> 4 ) );
	return raw_pressure;
}

i32 BME280_native_read_hum() {
	int32_t raw_humidity = 
							(int32_t)( (((uint32_t)meas_buffer[6]) << 8 ) | 
										((uint32_t)meas_buffer[7]) );
	return raw_humidity;
}

u32 BME280_native_get_dig_T1() {
	return calib.dig_T1;
}

i32 BME280_native_get_dig_T2() {
	return calib.dig_T2;
}

i32 BME280_native_get_dig_T3() {
	return calib.dig_T3;
}

u32 BME280_native_get_dig_P1() {
	return calib.dig_P1;
}

i32 BME280_native_get_dig_P2() {
	return calib.dig_P2;
}

i32 BME280_native_get_dig_P3() {
	return calib.dig_P3;
}

i32 BME280_native_get_dig_P4() {
	return calib.dig_P4;
}

i32 BME280_native_get_dig_P5() {
	return calib.dig_P5;
}

i32 BME280_native_get_dig_P6() {
	return calib.dig_P6;
}

i32 BME280_native_get_dig_P7() {
	return calib.dig_P7;
}

i32 BME280_native_get_dig_P8() {
	return calib.dig_P8;
}

i32 BME280_native_get_dig_P9() {
	return calib.dig_P9;
}

u32 BME280_native_get_dig_H1() {
	return calib.dig_H1;
}

i32 BME280_native_get_dig_H2() {
	return calib.dig_H2;
}

u32 BME280_native_get_dig_H3() {
	return calib.dig_H3;
}

i32 BME280_native_get_dig_H4() {
	return calib.dig_H4;
}

i32 BME280_native_get_dig_H5() {
	return calib.dig_H5;
}

i32 BME280_native_get_dig_H6() {
	return calib.dig_H6;
}

// First implementation in busy loop then we go to powerdown()
void FW_native_delay( i32 delay ) {
    i32 base = TIM2_base();
    while( TIM2_diff( base ) < delay ) {
        printf( "FW_native_delay: powerdown\xD\xA" );
        powerdown();
        printf( "FW_native_delay: power up\xD\xA" );
    }
}
