package ru.otus.homework16.msg;

public abstract class Msg {
    public static final String CLASS_NAME_VARIABLE = "className";

    protected final String className;
    protected String from;
    protected String to;

    protected Msg(Class<?> clazz) {
        this.className = clazz.getName();
    }

    protected Msg(Class<?> clazz, String from, String to){
        this(clazz);
        this.from = from;
        this.to = to;
    }

    public String getFrom(){
        return from;
    }

    public String getTo(){
        return to;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "className='" + className + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                '}';
    }
}
