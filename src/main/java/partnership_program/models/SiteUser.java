package partnership_program.models;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="siteuser")
public class SiteUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_line")
    private Long first_line;

    @Column(name = "second_line")
    private Long second_line;

    @Column(name = "third_line")
    private Long third_line;

    @Column(name = "balance")
    private Double balance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFirst_line() {
        return first_line;
    }

    public void setFirst_line(Long first_line) {
        this.first_line = first_line;
    }

    public Long getSecond_line() {
        return second_line;
    }

    public void setSecond_line(Long second_line) {
        this.second_line = second_line;
    }

    public Long getThird_line() {
        return third_line;
    }

    public void setThird_line(Long third_line) {
        this.third_line = third_line;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SiteUser siteUser = (SiteUser) o;
        return Objects.equals(id, siteUser.id) &&
                Objects.equals(first_line, siteUser.first_line) &&
                Objects.equals(second_line, siteUser.second_line) &&
                Objects.equals(third_line, siteUser.third_line) &&
                Objects.equals(balance, siteUser.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, first_line, second_line, third_line, balance);
    }

    public SiteUser() {
    }


    public void addBalance(Double summ){
        if (this.balance!=null) {
            this.balance += summ;
        }
        else balance = summ;
    }

}
