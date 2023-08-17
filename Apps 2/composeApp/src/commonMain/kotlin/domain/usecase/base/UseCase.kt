package domain.usecase.base

import domain.model.PremiumContent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Created by Ashwani Kumar Singh on 25,July,2023.
 */
abstract class UseCase<Type, in Params> where Type: Any {

    abstract suspend fun run (params: Params? = null): Type

    fun invoke(scope: CoroutineScope, params: Params?, onResult: UseCaseResponse<Type>?) {

        scope.launch {
            onResult?.onLoading(true)
            try {
                val result = run(params)
                onResult?.onSuccess(result)
                onResult?.onLoading(false)
            } catch (e: Exception) {
                e.message?.let { onResult?.onError(it) }
                onResult?.onLoading(false)
            }
        }

    }

}