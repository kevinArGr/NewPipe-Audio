package us.shandian.giga.get;

import org.schabi.newpipe.extractor.stream.AudioStream;
import org.schabi.newpipe.extractor.stream.DeliveryMethod;

import java.util.Objects;

/** Keeps URL recovery within the audio track chosen when the download was created. */
public final class AudioStreamRecoveryMatcher {
    private AudioStreamRecoveryMatcher() {
    }

    public static boolean matches(final AudioStream audio, final MissionRecoveryInfo recovery) {
        return audio.getAverageBitrate() == recovery.getDesiredBitrate()
                && audio.getFormat() == recovery.getFormat()
                && Objects.equals(audio.getAudioTrackId(), recovery.getDesired())
                && audio.getDeliveryMethod() == DeliveryMethod.PROGRESSIVE_HTTP;
    }
}
