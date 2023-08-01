import UIKit
import SwiftUI
import shared

struct ComposeView: UIViewControllerRepresentable {
    
    func createViewModel() -> SectionListViewModel {
        var apiRequest = ApiRequest()
        var sectionsListRepoImpl = SectionsListRepoImpl(apiRequest: apiRequest)
        var sectionListUseCase = SectionListUseCase(sectionsListRepo: sectionsListRepoImpl)

        var sectionContentRepoImpl = SectionContentRepoImpl(apiRequest: apiRequest)
        var sectionContentUseCase = SectionContentUseCase(sectionContentRepo: sectionContentRepoImpl)

        var sectionListViewModel = SectionListViewModel(sectionListUseCase: sectionListUseCase,
                    sectionContentUseCase: sectionContentUseCase)
        return sectionListViewModel
    }
    
    
    func makeUIViewController(context: Context) -> UIViewController {
        
        Main_iosKt.MainViewController(sectionListViewModel: createViewModel())
    }
    
    
    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
                .ignoresSafeArea(.all, edges: .bottom) // Compose has own keyboard handler
    }
}



