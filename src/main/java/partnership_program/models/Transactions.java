package partnership_program.models;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name="transactions")
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "userid")
    private Long userId;

    @Column(name = "amountBuy")
    private Double amaountBuy;

    @Column(name = "amountSpend")
    private Double amountSpend;

    @Column(name = "date")
    private LocalDateTime date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getAmaountBuy() {
        return amaountBuy;
    }

    public void setAmaountBuy(Double amaountBuy) {
        this.amaountBuy = amaountBuy;
    }

    public Double getAmountSpend() {
        return amountSpend;
    }

    public void setAmountSpend(Double amountSpend) {
        this.amountSpend = amountSpend;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transactions that = (Transactions) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(amaountBuy, that.amaountBuy) &&
                Objects.equals(amountSpend, that.amountSpend) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, amaountBuy, amountSpend, date);
    }

    public Transactions() {
    }

}
