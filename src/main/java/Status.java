public abstract class Status<T> {

    public static <T> Status<T> success(T data) {
        return new SuccessStatus<>(data);
    }

    public static <T> Status<T> failure(int code, String message) {
        return new FailureStatus<>(code, message);
    }

    public abstract T getData();

    public abstract StatusError getError();

    private static class SuccessStatus<T> extends Status<T> {
        private final T data;

        SuccessStatus(T data) {
            this.data = data;
        }

        @Override
        public T getData() {
            return data;
        }

        @Override
        public StatusError getError() {
            throw new RuntimeException("Cannot get error of a successful status");
        }
    }

    private static class FailureStatus<T> extends Status<T> {
        private final StatusError error;

        public FailureStatus(int code, String message) {
            this.error = new StatusError(code, message);
        }

        @Override
        public T getData() {
            throw new RuntimeException("Cannot get data of an unsuccessful status");
        }

        @Override
        public StatusError getError() {
            return this.error;
        }
    }
}
