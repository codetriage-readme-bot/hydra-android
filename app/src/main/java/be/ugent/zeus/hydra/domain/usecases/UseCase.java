package be.ugent.zeus.hydra.domain.usecases;

/**
 * A use case where the execution does not throw an exception.
 *
 * @param <IN>  The input parameters for the use case.
 * @param <OUT> The output of the use case.
 *
 * @author Niko Strijbol
 */
public interface UseCase<IN, OUT> extends ExceptionUseCase<IN, OUT, Exception> {

    @Override
    OUT execute(IN arguments);
}