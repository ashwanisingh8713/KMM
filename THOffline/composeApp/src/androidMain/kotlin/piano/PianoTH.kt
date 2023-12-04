import android.content.Context
import io.piano.android.composer.Composer
import io.piano.android.composer.c1x.PianoC1X
import io.piano.android.id.PianoId
import io.piano.android.id.PianoIdJs
import io.piano.android.id.facebook.FacebookOAuthProvider
import io.piano.android.id.google.GoogleOAuthProvider
import io.piano.android.id.models.PianoIdAuthFailureResult
import io.piano.android.id.models.PianoIdAuthSuccessResult
import timber.log.Timber

/**
 * Created by Ashwani Kumar Singh on 15,September,2023.
 */

class PianoTH {

    fun initPianoTH(context: Context) {
        var IS_PIANO_PRODUCTION = true

        var PIANO_ENDPOINT = PianoId.ENDPOINT_SANDBOX
        var PIANO_AID: String? = "o6j4tQ91su"
        var COMPOSER_ENDPOINT = Composer.Endpoint.SANDBOX
        var PIANO_SITE_ID: String? = "1139723590764420184"
        if (IS_PIANO_PRODUCTION) { // Piano's Production IDs
            PIANO_AID = "DC3REpZYpu"
            PIANO_ENDPOINT = PianoId.ENDPOINT_PRODUCTION
            COMPOSER_ENDPOINT = Composer.Endpoint.PRODUCTION
            PIANO_SITE_ID = "1138611009236567610"
        }

        PianoId.init(PIANO_ENDPOINT, PIANO_AID!!)
            .with { r ->
                when (r) {
                    is PianoIdAuthSuccessResult -> {
                        Composer.getInstance().userToken(r.token?.accessToken)
                    }

                    is PianoIdAuthFailureResult -> Timber.e(r.exception)
                }
            }
            .with(object : PianoIdJs {
                override fun customEvent(eventData: String) {
                    Timber.d("Custom event: %s", eventData)
                }
            })
            .with(GoogleOAuthProvider())
            .with(FacebookOAuthProvider())
        // Use this one if you want Piano C1X integration
        PianoC1X.init(context, PIANO_SITE_ID!!, PIANO_AID, COMPOSER_ENDPOINT)
        // Use this one for Composer only
//        Composer.init(this, BuildConfig.PIANO_AID, COMPOSER_ENDPOINT, pianoConsents)
//        Composer.init(this, PIANO_AID, COMPOSER_ENDPOINT)

    }
}