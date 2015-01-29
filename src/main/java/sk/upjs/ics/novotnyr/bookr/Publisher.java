package sk.upjs.ics.novotnyr.bookr;


import java.net.URL;

public class Publisher {

    private static final long NULL_PUBLISHER_ID = Long.MIN_VALUE;

    private Long id;

    private String name;

    private URL web;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URL getWeb() {
        return web;
    }

    public void setWeb(URL web) {
        this.web = web;
    }

    @Override
    public String toString() {
        return "Publisher{" + "id=" + id + ", name=" + name + ", web=" + web + '}';
    }

    public static Publisher getNullPublisher() {
        Publisher publisher = new Publisher();
        publisher.setId(NULL_PUBLISHER_ID);

        return publisher;
    }

    public static boolean isNullPublisher(Publisher publisher) {
        return NULL_PUBLISHER_ID == publisher.getId();
    }

}
