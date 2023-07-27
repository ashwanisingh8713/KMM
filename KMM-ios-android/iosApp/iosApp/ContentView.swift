import UIKit
import SwiftUI
import shared

struct ComposeView: UIViewControllerRepresentable {
    
    func createViewModel() -> SectionListViewModel {
        var apiRequest = ApiRequest()
        var sectionsListRepoImpl = SectionsListRepoImpl(apiRequest: apiRequest)
        var sectionListUseCase = SectionListUseCase(sectionsListRepo: sectionsListRepoImpl)
        var sectionListViewModel = SectionListViewModel(sectionListUseCase: sectionListUseCase)
        return sectionListViewModel
    }
    
    
    func makeUIViewController(context: Context) -> UIViewController {
        
        Main_iosKt.MainViewController(sectionListViewModel: createViewModel())
    }
    
    func myFunc() -> Void {
        var apiRequestt = ApiRequest()
        var sectionsListRepoImpl = SectionsListRepoImpl(apiRequest: apiRequestt)
        var sectionListUseCase = SectionListUseCase(sectionsListRepo: sectionsListRepoImpl)
        var sectionListViewModel = SectionListViewModel(sectionListUseCase: sectionListUseCase)
        Test_iosKt.CreateApiRequest()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
                .ignoresSafeArea(.all, edges: .bottom) // Compose has own keyboard handler
    }
}



