
IF("${ARCH}" STREQUAL "arm")
SET(CMAKE_ASM_FLAGS "${CFLAGS} -x assembler-with-cpp")
target_sources(rlottie
    PRIVATE
        "pixman-arm-neon-asm.S"
    )
ENDIF("${ARCH}" STREQUAL "arm")

target_include_directories(rlottie
    PRIVATE
        "."
    )
