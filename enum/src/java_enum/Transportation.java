package java_enum;

public enum Transportation {
    BUS(100) {
        public int fare(int distance) {
            return BASIC_FAIR * distance;
        }
    }, TRAIN(150) {
        public int fare(int distance) {
            return BASIC_FAIR * distance;
        }
    }, SHIP(100) {
        public int fare(int distance) {
            return BASIC_FAIR * distance;
        }
    }, AIRPLANE(300) {
        public int fare(int distance) {
            return BASIC_FAIR * distance;
        }
    };

    // protected로 설정해야 추상메소드 구현시 인식 가능
    protected final int BASIC_FAIR;

    private Transportation(int fair) {
        BASIC_FAIR = fair;
    }

    public abstract int fare(int distance);
}
