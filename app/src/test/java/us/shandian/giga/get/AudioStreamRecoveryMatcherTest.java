package us.shandian.giga.get;

import org.junit.Test;
import org.schabi.newpipe.extractor.MediaFormat;
import org.schabi.newpipe.extractor.stream.AudioStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AudioStreamRecoveryMatcherTest {
    private static AudioStream audio(final String trackId, final int bitrate) {
        return new AudioStream.Builder()
                .setId("140")
                .setContent("https://example.com/audio", true)
                .setMediaFormat(MediaFormat.M4A)
                .setAverageBitrate(bitrate)
                .setAudioTrackId(trackId)
                .build();
    }

    @Test
    public void preservesDubbedTrackWhenOriginalHasSameQuality() {
        final MissionRecoveryInfo recovery = new MissionRecoveryInfo(audio("es.1", 128));
        assertEquals("es.1", recovery.getDesired());
        assertFalse(AudioStreamRecoveryMatcher.matches(audio("en.0", 128), recovery));
        assertTrue(AudioStreamRecoveryMatcher.matches(audio("es.1", 128), recovery));
    }

    @Test
    public void rejectsOtherTrackEvenIfLanguageIsTheSame() {
        final MissionRecoveryInfo recovery = new MissionRecoveryInfo(audio("es.1", 128));
        assertFalse(AudioStreamRecoveryMatcher.matches(audio("es.2", 128), recovery));
        assertFalse(AudioStreamRecoveryMatcher.matches(audio("es.1", 64), recovery));
    }

    @Test
    public void missingTrackDoesNotFallBackToAnotherLanguage() {
        final MissionRecoveryInfo recovery = new MissionRecoveryInfo(audio(null, 128));
        assertTrue(AudioStreamRecoveryMatcher.matches(audio(null, 128), recovery));
        assertFalse(AudioStreamRecoveryMatcher.matches(audio("es.1", 128), recovery));
    }
}
