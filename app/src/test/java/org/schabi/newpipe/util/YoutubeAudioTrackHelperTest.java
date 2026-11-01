package org.schabi.newpipe.util;

import org.junit.Test;
import org.schabi.newpipe.extractor.services.youtube.ItagItem;
import org.schabi.newpipe.extractor.services.youtube.protos.video.Xtags.KeyValuePair;
import org.schabi.newpipe.extractor.services.youtube.protos.video.Xtags.XTags;
import org.schabi.newpipe.extractor.stream.AudioStream;

import java.util.Base64;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class YoutubeAudioTrackHelperTest {
    private static String tags(final String key, final String value) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(XTags.newBuilder()
                .addXtags(KeyValuePair.newBuilder().setKey(key).setValue(value))
                .build().toByteArray());
    }

    @Test
    public void identifiesAutomaticDubFromYoutubeMetadata() {
        assertTrue(YoutubeAudioTrackHelper.hasAutoDubbedTag(tags("acont", "dubbed-auto")));
    }

    @Test
    public void readsMetadataRetainedByTheExtractedAudioStream() throws Exception {
        final ItagItem itag = ItagItem.getItag(140);
        itag.setXtags(tags("acont", "dubbed-auto"));
        final AudioStream stream = new AudioStream.Builder()
                .setId("140")
                .setContent("https://example.com/audio", true)
                .setItagItem(itag)
                .build();
        assertTrue(YoutubeAudioTrackHelper.isAutoDubbed(stream));
        itag.setXtags(tags("acont", "dubbed"));
        assertFalse(YoutubeAudioTrackHelper.isAutoDubbed(stream));
    }

    @Test
    public void doesNotMislabelOriginalOrManualDub() {
        assertFalse(YoutubeAudioTrackHelper.hasAutoDubbedTag(tags("acont", "original")));
        assertFalse(YoutubeAudioTrackHelper.hasAutoDubbedTag(tags("acont", "dubbed")));
        assertFalse(YoutubeAudioTrackHelper.hasAutoDubbedTag(tags("other", "dubbed-auto")));
    }

    @Test
    public void toleratesMissingOrInvalidMetadata() {
        assertFalse(YoutubeAudioTrackHelper.hasAutoDubbedTag(null));
        assertFalse(YoutubeAudioTrackHelper.hasAutoDubbedTag(""));
        assertFalse(YoutubeAudioTrackHelper.hasAutoDubbedTag("%%%"));
        assertFalse(YoutubeAudioTrackHelper.hasAutoDubbedTag("AA"));
    }
}
