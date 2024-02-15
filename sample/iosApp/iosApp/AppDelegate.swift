//
//  AppDelegate.swift
//  iosApp
//
//  Created by Ismail Mohamed on 15/02/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import UIKit
import Lottie


class AppDelegate : NSObject, UIApplicationDelegate {
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
        LottieConfiguration.shared.renderingEngine = .mainThread
        return true
    }
}
