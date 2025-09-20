package app.exception;

/**
 * 指定された条件に対応するモデルが見つからなかった場合にスローされる例外。
 */
public class ModelNotFoundException extends RuntimeException {
    public ModelNotFoundException(String message) {
        super(message);
    }
}
