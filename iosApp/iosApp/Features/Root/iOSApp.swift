import UIKit
import SwiftUI
import iosExport

@main
struct iOSApp: App {
    init() {
        IosAppKt.InitKoin()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}