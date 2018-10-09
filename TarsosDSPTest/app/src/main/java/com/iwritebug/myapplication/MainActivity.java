package com.iwritebug.myapplication;

import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.File;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.FadeIn;
import be.tarsos.dsp.FadeOut;
import be.tarsos.dsp.PitchShifter;
import be.tarsos.dsp.WaveformSimilarityBasedOverlapAdd;
import be.tarsos.dsp.effects.DelayEffect;
import be.tarsos.dsp.effects.FlangerEffect;
import be.tarsos.dsp.filters.LowPassFS;
import be.tarsos.dsp.io.android.AndroidFFMPEGLocator;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;
import be.tarsos.dsp.synthesis.AmplitudeLFO;
import be.tarsos.dsp.synthesis.NoiseGenerator;
import be.tarsos.dsp.synthesis.SineGenerator;
import be.tarsos.dsp.util.PitchConverter;


public class MainActivity extends AppCompatActivity {

    AudioDispatcher adp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new AndroidFFMPEGLocator(this);
    }

    public void onPlay(View view) {
        File mp3 = new File("/storage/emulated/0/netease/cloudmusic/Music/Hitchhiker - 11 (ELEVEN).MP3");
        adp = AudioDispatcherFactory.fromPipe(mp3.getAbsolutePath(),44100,5000,2500);
        adp.addAudioProcessor(new PitchShifter(1.1,44100,5000,2500));

        //adp.addAudioProcessor(new FadeIn(10));
        //adp.addAudioProcessor(new NoiseGenerator(8.0));
        //adp.addAudioProcessor(new LowPassFS(1000,44100));
        //adp.addAudioProcessor(new SineGenerator(0.05,220));
        //adp.addAudioProcessor(new AmplitudeLFO(0.1,0.7));
        //adp.addAudioProcessor(new DelayEffect(1.5, 0.4, 44100));
        /*
        adp.addAudioProcessor(new SineGenerator(0.05,220));
        adp.addAudioProcessor(new AmplitudeLFO(10,0.9));
        adp.addAudioProcessor(new SineGenerator(0.2,440));
        adp.addAudioProcessor(new SineGenerator(0.1,880));
        adp.addAudioProcessor(new DelayEffect(1.5, 0.4, 44100));
        adp.addAudioProcessor(new AmplitudeLFO());
        adp.addAudioProcessor(new NoiseGenerator(0.02));
        adp.addAudioProcessor(new SineGenerator(0.05,1760));
        adp.addAudioProcessor(new SineGenerator(0.01,2460));
        adp.addAudioProcessor(new AmplitudeLFO(0.1,0.7));*/
        //adp.addAudioProcessor(new FlangerEffect(0.1,0.2,44100,4));
        adp.addAudioProcessor(new AndroidAudioPlayer(adp.getFormat(),5000, AudioManager.STREAM_MUSIC));

        //adp.addAudioProcessor(new NoiseGenerator(1000));
        //adp.addAudioProcessor(new DelayEffect(300, 0.5, 44100));

        /*adp.addAudioProcessor(new NoiseGenerator(0.2));
        adp.addAudioProcessor(new LowPassFS(1000,44100));
        adp.addAudioProcessor(new LowPassFS(1000,44100));
        adp.addAudioProcessor(new LowPassFS(1000,44100));
        adp.addAudioProcessor(new SineGenerator(0.05,220));
        adp.addAudioProcessor(new AmplitudeLFO(10,0.9));
        adp.addAudioProcessor(new SineGenerator(0.2,440));
        adp.addAudioProcessor(new SineGenerator(0.1,880));
        adp.addAudioProcessor(new DelayEffect(1.5, 0.4, 44100));
        adp.addAudioProcessor(new AmplitudeLFO());
        adp.addAudioProcessor(new NoiseGenerator(0.02));
        adp.addAudioProcessor(new SineGenerator(0.05,1760));
        adp.addAudioProcessor(new SineGenerator(0.01,2460));
        adp.addAudioProcessor(new AmplitudeLFO(0.1,0.7));

        adp.addAudioProcessor(new FlangerEffect(0.1,0.2,44100,4));*/
        /*adp.addAudioProcessor(new PitchProcessor());
        adp.addAudioProcessor(new PitchProcessor(
                PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, 44100, frameSize,
                new PitchDetectionHandler() {

                    public void handlePitch(
                            PitchDetectionResult pitchDetectionResult,
                            AudioEvent audioEvent) {
                        if (pitchDetectionResult.isPitched()) {
                            float[] pitch = new float[1];
                            pitch[0] = (float) PitchConverter
                                    .hertzToAbsoluteCent(pitchDetectionResult
                                            .getPitch());
                            fe.put(audioEvent.getTimeStamp() - timeLag,
                                    pitch);
                        }
                    }
                }));*/
        new Thread(adp).start();
    }

    public void onStop(View view) {
        adp.stop();
    }
}
