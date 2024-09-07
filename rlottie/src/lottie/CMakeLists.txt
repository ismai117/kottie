add_subdirectory(zip)

target_sources(rlottie
    PRIVATE
        "lottieitem.cpp"
        "lottieitem_capi.cpp"
        "lottieloader.cpp"
        "lottiemodel.cpp"
        "lottieproxymodel.cpp"
        "lottieparser.cpp"
        "lottieanimation.cpp"
        "lottiekeypath.cpp"
    )

target_include_directories(rlottie
    PRIVATE
        "."
    )
