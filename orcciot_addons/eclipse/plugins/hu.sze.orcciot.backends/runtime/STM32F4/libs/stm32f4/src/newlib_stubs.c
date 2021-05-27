#include <sys/fcntl.h>
#include <sys/stat.h>
#include <sys/time.h>
#include <sys/times.h>
#include <sys/unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <errno.h>
#include <_ansi.h>
#include <stdio.h>
#include <time.h>
#include <reent.h>
#include <signal.h>
#include <unistd.h>
#include "stm32f4xx_usart.h"

/* #define EMPTY_STUBS 1 */
/*

 read

 Read a character to a file. `libc' subroutines will use this system routine for input from all files, including stdin

 Returns -1 on error or blocks until the number of characters have been read.

 */


int _read(int file, char *ptr, int len) {
    int n;
    int num = 0;
    switch (file) {
    case STDIN_FILENO:
        for (n = 0; n < len; n++) {
            while ((USART6->SR & USART_FLAG_RXNE) == (uint16_t)RESET) {}
            char c = (char)(USART6->DR & (uint16_t)0x01FF);
            *ptr++ = c;
            num++;
        }
        break;
    default:
        errno = EBADF;
        return -1;
    }
    return num;
}

/*

 write

 Write a character to a file. `libc' subroutines will use this system routine for output to all files, including stdout

 Returns -1 on error or number of bytes sent

 */
int _write(int file, char *ptr, int len) {
    int n;
    uint16_t	c;

    switch (file) {
    case STDOUT_FILENO: /*stdout*/
        for (n = 0; n < len; n++) {
	    c = (uint16_t)( *(ptr++) );
	    while(USART_GetFlagStatus(USART6, USART_FLAG_TXE) == RESET); 
	    USART_SendData(USART6, c);		
	}
        break;
    case STDERR_FILENO: /* stderr */
        for (n = 0; n < len; n++) {
	    c = (uint16_t)( *(ptr++) );
	    while(USART_GetFlagStatus(USART6, USART_FLAG_TXE) == RESET); 
	    USART_SendData(USART6, c);		
	}
        break;
    default:
        errno = EBADF;
        return -1;
    }
    return len;
}

#ifdef EMPTY_STUBS
int     _system         (const char *p) {
    return 0;
}

int     _rename         (const char *p1, const char *p2) {
    return 0;
}

int     _isatty         (int a) {
    return 0;
}

clock_t _times          (struct tms *t) {
        return (clock_t)0;
}

int     _gettimeofday   (struct timeval *t, void *p) {
    return 0;
}

void    _raise          (void) {}

int     _unlink         (const char *p) {
    return 0;
}

int     _link           (const char *p1, const char *p2) {
    return 0;
}

int     _stat           (const char *p1, struct stat *s) {
    return 0;
}

int     _fstat          (int a, struct stat *s) {
    return 0;
}

void *  _sbrk           (ptrdiff_t s) {
    return (void *)NULL;
}

pid_t   _getpid         (void) {
    return (pid_t)0;
}

int     _kill           (int a, int b) {
    return 0;
}

void    _exit           (int a) {
}

int     _close          (int a) {
        return 0;
}

int     _swiclose       (int a) {
        return 0;
}

int     _open           (const char *p, int a, ...) {
    return 0;
}

int     _swiopen        (const char *p, int a) {
    return 0;
}

int     _swiwrite       (int a, const void *p, size_t b) {
    return 0;
}

_off_t  _lseek          (int a, _off_t b, int c) {
    return (_off_t)0;
}

_off_t  _swilseek       (int a, _off_t b, int c) {
    return (_off_t)0;
}

int     _swiread        (int a, void *p, size_t b) {
    return 0;
}
#endif
