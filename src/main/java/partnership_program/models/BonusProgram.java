package partnership_program.models;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="bonusprogram")
public class BonusProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "user")
    private Long user;

//    @Column(name = "first_line")
//    private Long first_line;
//
//    @Column(name = "second_line")
//    private Long second_line;
//
//    @Column(name = "third_line")
//    private Long third_line;

//    @Column(name = "value")
//    private Long value;

//    @Column(name = "balance")
//    private Double balance;

    @Column(name = "firstLineBalance")
    private Double firstLineBalance = 0.0;

    @Column(name = "secondLineBalance")
    private Double secondLineBalance = 0.0;

    @Column(name = "firstLineEnabled")
    private Boolean firstLineEnabled = false;

    @Column(name = "secondLineEnabled")
    private Boolean secondLineEnabled = false;

    @Column(name = "thirdLineEnabled")
    private Boolean thirdLineEnabled = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getFirstLineBalance() {
        if (this.firstLineBalance != null)
        return firstLineBalance;
        return 0.0;
    }

    public void setFirstLineBalance(Double firstLineBalance) {
        this.firstLineBalance = firstLineBalance;
    }

    public Double getSecondLineBalance() {
        return secondLineBalance;
    }

    public void setSecondLineBalance(Double secondLineBalance) {
        this.secondLineBalance = secondLineBalance;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public Boolean getFirstLineEnabled() {
        return firstLineEnabled;
    }

    public void setFirstLineEnabled(Boolean firstLineEnabled) {
        this.firstLineEnabled = firstLineEnabled;
    }

    public Boolean getSecondLineEnabled() {
        return secondLineEnabled;
    }

    public void setSecondLineEnabled(Boolean secondLineEnabled) {
        this.secondLineEnabled = secondLineEnabled;
    }

    public Boolean getThirdLineEnabled() {
        return thirdLineEnabled;
    }

    public void setThirdLineEnabled(Boolean thirdLineEnabled) {
        this.thirdLineEnabled = thirdLineEnabled;
    }

    public BonusProgram() {
    }

    //Сумуємо вистрати всіх юзерів по першій лінії
    public void addFromFirstLine(Double summ){
        if (this.firstLineBalance != null)
            if (this.firstLineBalance <= 5000)
            this.firstLineBalance+=summ;
        else this.firstLineBalance = summ;

    }

    //Сумуємо витрати юзерів по всіх лініях
    public void addAllBalance(Double summ){
        if (this.secondLineBalance != null)
            if (this.secondLineBalance <= 10000)
        this.secondLineBalance+=summ;
        else this.secondLineBalance = summ;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BonusProgram that = (BonusProgram) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(user, that.user) &&
                Objects.equals(firstLineBalance, that.firstLineBalance) &&
                Objects.equals(secondLineBalance, that.secondLineBalance) &&
                Objects.equals(firstLineEnabled, that.firstLineEnabled) &&
                Objects.equals(secondLineEnabled, that.secondLineEnabled) &&
                Objects.equals(thirdLineEnabled, that.thirdLineEnabled);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, firstLineBalance, secondLineBalance, firstLineEnabled, secondLineEnabled, thirdLineEnabled);
    }
}
