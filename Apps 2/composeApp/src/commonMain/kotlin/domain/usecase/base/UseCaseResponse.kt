package domain.usecase.base

/**
 * Created by Ashwani Kumar Singh on 25,July,2023.
 */
interface UseCaseResponse<Type> {
    fun onSuccess(type: Type)
    fun onError(apiError: String)
    fun onLoading(isLoading: Boolean)
}