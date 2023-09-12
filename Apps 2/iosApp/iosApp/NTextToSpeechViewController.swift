//
//  NTTSViewController.swift
//  TheHindu
//
//  Created by Rupam on 08/09/23.
//  Copyright © 2023 Rupam Bharath. All rights reserved.
//

import UIKit
import AVFoundation

struct NConstants {
    static let kRate = "Rate"
    static let kPitch = "Pitch"
    static let kLanguageCode = "LanguageCode"
    static let kPreferredVoiceLanguageCode = "en-GB"
}

class NTextToSpeechViewController: UIPageViewController {

    @IBOutlet weak var textToSpeechButton: UIButton!
    
    private var isTextToSpeechActive = false
    private let speechSynthesizer = AVSpeechSynthesizer()
    //private var rate: Float!
    //private var pitch: Float!
    //private var preferredVoiceLanguageCode: String!

    // MARK: - Override methods


    override func viewDidLoad() {
        super.viewDidLoad()


    }
    
    deinit {
        self.stopSpeech()
        NotificationCenter.default.removeObserver(self)
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        self.stopSpeech()
        super.viewWillDisappear(true)
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        
    }
    

    // MARK: - TTS Methods
    
    private func startSpeechWith(title: String?, leadText: String?, description:String?){
        speechSynthesizer.delegate = self
        let rate = AVSpeechUtteranceDefaultSpeechRate
        let pitch = 1.0
        let preferredVoiceLanguageCode = "en-GB"
        do {
            try AVAudioSession.sharedInstance().setCategory(.playback, mode: .default)
            try AVAudioSession.sharedInstance().setActive(true)
        } catch {
            //print(error)
        }
        
        isTextToSpeechActive = true
        //self.textToSpeechButton.setImage(UIImage.init(named: "audioPause")?.withRenderingMode(.alwaysTemplate), for: .normal)
        
        if !speechSynthesizer.isSpeaking{
            
            var articleToRead = String()
            if let articleTitle = title{
                articleToRead.append(articleTitle)
                articleToRead.append(". ")
            }
            if let articleLead = leadText{
                articleToRead.append(articleLead)
                articleToRead.append(". ")
            }
            if let articleBody = description{
                articleToRead.append(articleBody)
            }
            
            let speechUtterance = AVSpeechUtterance(string: articleToRead)
            
            speechUtterance.rate = rate
            speechUtterance.pitchMultiplier = Float(pitch)
            speechUtterance.volume = 1.0
            let voice = AVSpeechSynthesisVoice(language: preferredVoiceLanguageCode)
            speechUtterance.voice = voice
            if isTextToSpeechActive {
                speechSynthesizer.speak(speechUtterance)
            }
        }else{
            if isTextToSpeechActive {
                speechSynthesizer.continueSpeaking()
            }
        }
    }
    
    private func pauseSpeech() {
        isTextToSpeechActive = false
        //self.textToSpeechButton.setImage(UIImage.init(named: "audioNormal")?.withRenderingMode(.alwaysTemplate), for: .normal)
        speechSynthesizer.pauseSpeaking(at: AVSpeechBoundary.word)
    }
    
    
    private func stopSpeech() {
        isTextToSpeechActive = false
        //self.textToSpeechButton.setImage(UIImage.init(named: "audioNormal")?.withRenderingMode(.alwaysTemplate), for: .normal)
        speechSynthesizer.stopSpeaking(at: AVSpeechBoundary.immediate)
    }
    
    // MARK: - CTA Methods
    
//    @IBAction private func textToSpeechBtnClicked(_ sender: Any) {
//        if !isTextToSpeechActive {
//            self.startSpeech()
//        }else{
//            self.pauseSpeech()
//        }
//    }
    
}

extension NTextToSpeechViewController: AVSpeechSynthesizerDelegate{
    func speechSynthesizer(_ synthesizer: AVSpeechSynthesizer, didFinish utterance: AVSpeechUtterance) {
        isTextToSpeechActive = false
        //self.textToSpeechButton.setImage(UIImage.init(named: "audioNormal")?.withRenderingMode(.alwaysTemplate), for: .normal)
    }
}
