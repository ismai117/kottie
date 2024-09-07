add_subdirectory(freetype)
add_subdirectory(pixman)
add_subdirectory(stb)


target_sources(rlottie
    PRIVATE
        "vrect.cpp"
        "vdasher.cpp"
        "vbrush.cpp"
        "vbitmap.cpp"
        "vpainter.cpp"
        "vdrawhelper_common.cpp"
        "vdrawhelper.cpp"
        "vdrawhelper_sse2.cpp"
        "vdrawhelper_neon.cpp"
        "vrle.cpp"
        "vpath.cpp"
        "vpathmesure.cpp"
        "vmatrix.cpp"
        "velapsedtimer.cpp"
        "vdebug.cpp"
        "vinterpolator.cpp"
        "vbezier.cpp"
        "vraster.cpp"
        "vdrawable.cpp"
        "vimageloader.cpp"
        "varenaalloc.cpp"
    )

target_include_directories(rlottie
    PRIVATE
        "."
    )
