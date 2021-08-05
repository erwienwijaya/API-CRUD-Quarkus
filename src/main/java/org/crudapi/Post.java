package org.crudapi;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "POST")
public class Post extends PanacheEntityBase {
    @Id
    @GeneratedValue
    public Long id;
    @Column(length=50)

    public String title;

    @Column(length=200)
    public String content;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "Post_Tag",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    private Set<Tag> tags = new HashSet<>();

//    Getter and Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<Tag> getTags() {
        return tags;
    }

//    @Override
//    public String toString() {
//        return "Post {" +
//                "id=" + id +
//                ",title='" + title + "'" +
//                ",content='" + content + "'}";
//    }

}
