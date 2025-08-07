package day3;

public class Book {
    private String title;
    private String author;
    private boolean isIssued;
    private User issuedTo;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.isIssued = false;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isIssued() {
        return isIssued;
    }
    

    public void setIssued(boolean issued) {
        this.isIssued = issued;
    }
    public void setIssuedTo(User user) {
        this.issuedTo = user;
    }
    public User getIssuedTo() {
        return issuedTo;
    }
}
