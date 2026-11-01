package org.schabi.newpipe.util;

import androidx.annotation.Nullable;

import com.google.protobuf.InvalidProtocolBufferException;

import org.schabi.newpipe.extractor.services.youtube.protos.video.Xtags.XTags;
import org.schabi.newpipe.extractor.stream.AudioStream;

import java.util.Base64;

/** Reads YouTube's automatic dubbing marker without guessing from translated track names. */
public final class YoutubeAudioTrackHelper {
    private YoutubeAudioTrackHelper() {
    }

    public static boolean isAutoDubbed(final AudioStream stream) {
        return stream.getItagItem() != null
                && hasAutoDubbedTag(stream.getItagItem().getXtags());
    }

    static boolean hasAutoDubbedTag(@Nullable final String xtags) {
        if (xtags == null) {
            return false;
        }
        try {
            return XTags.parseFrom(Base64.getUrlDecoder().decode(xtags))
                    .getXtagsList().stream()
                    .anyMatch(tag -> "acont".equals(tag.getKey())
                            && "dubbed-auto".equals(tag.getValue()));
        } catch (final InvalidProtocolBufferException | IllegalArgumentException ignored) {
            return false;
        }
    }
}
