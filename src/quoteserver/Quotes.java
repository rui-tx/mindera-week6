package quoteserver;

public enum Quotes {
    QUOTE_1("The future belongs to those who believe in the beauty of their dreams."),
    QUOTE_2("The greatest glory in living lies not in never falling, but in rising every time we fall."),
    QUOTE_3("The only way to do great work is to love what you do."),
    QUOTE_4("The future belongs to those who believe in the beauty of their dreams."),
    QUOTE_5("The greatest glory in living lies not in never falling, but in rising every time we fall."),
    QUOTE_6("The only way to do great work is to love what you do."),
    QUOTE_7("The future belongs to those who believe in the beauty of their dreams."),
    QUOTE_8("The greatest glory in living lies not in never falling, but in rising every time we fall."),
    QUOTE_9("The only way to do great work is to love what you do."),
    QUOTE_10("The future belongs to those who believe in the beauty of their dreams.");

    private final String quote;

    Quotes(String quote) {
        this.quote = quote;
    }

    public String getQuote() {
        return quote;
    }
}
