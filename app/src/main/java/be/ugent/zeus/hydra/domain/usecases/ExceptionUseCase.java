package be.ugent.zeus.hydra.domain.usecases;

/**
 * From <a href="https://en.wikipedia.org/wiki/Use_case">Wikipedia</a>: a use case is a list of actions or event steps
 * typically defining the interactions between a role and a system to achieve a goal.
 *
 * This interface represents the list of actions in the definition above. Another name could be a procedure that is
 * executed to do something.
 *
 * @param <IN> The input parameters for the use case.
 * @param <OUT> The output of the use case.
 * @param <E> The possible exception.
 *
 * @author Niko Strijbol
 */
public interface ExceptionUseCase<IN, OUT, E extends Exception> {

    /**
     * Execute the use case.
     *
     * @param arguments The input arguments. You must not pass dependencies to this method. You should only pass
     *                  arguments relevant to the current context in which the use case is executed. For example, you
     *                  should pass a User, but not a UserRepository.
     * @return The result of the use case.
     *
     * @throws E Possible exception.
     */
    OUT execute(IN arguments) throws E;
}