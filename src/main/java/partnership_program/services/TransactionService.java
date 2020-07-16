package partnership_program.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import partnership_program.dao.TransactionDao;
import partnership_program.models.BonusProgram;
import partnership_program.models.SiteUser;
import partnership_program.models.Transactions;

import java.util.List;

@Service
public class TransactionService implements UserDetailsService {

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private UserService userService;

    @Autowired
    private BonusProgramService bonusProgramService;

    //Після кожної транзакції в сутність BonusProgram буде зберігатися інформація до досягнення цільвих зночень для 2-ї і 3-ї ліній
    public void addAmount(SiteUser siteUser, Double amount){

        //Додаю всіх юзерів в один список, щоб всі зміни разом занести в БД, а не по одному
        List<BonusProgram> list =
                bonusProgramService.findByLinesUser(siteUser);
        for (BonusProgram bp : list) {
            bp.addAllBalance(amount);
        }

        //Тут ведеться додатковий облік транзакцій для першої ліній
        if (siteUser.getFirst_line() != null)
        bonusProgramService.findByUser(siteUser.getFirst_line()).addFromFirstLine(amount);

        bonusProgramService.save(list);
    }

    //Основна роль тут - збереження транзакції в БД (останній рядок). Все інше - логіка бонусних надхлджень
    public void save(Transactions transactions){
        //Вилучаємо юзера з транзакції
        SiteUser siteUser = userService.findUser(transactions.getUserId());

        //Одразу записую в БД інфу для прорахунків в майбутньому цільових показників 5000 і 10000
        addAmount(siteUser, transactions.getAmountSpend());

        //Кожну лінію обробляю по черзі.

        //Block for first line
        Double residual = 0.0; //це сума транзакції, від якої відраховуємо відсотки бонусів
        if (siteUser.getFirst_line() != null) {
            //шукаю прапорець, чи відноситься юзер до першої групи. Якщо так - поповнюємо його баланс і переходимо до другої лінії
            if (bonusProgramService.findByUser(siteUser.getFirst_line()).getFirstLineEnabled()) {
                residual = transactions.getAmountSpend();
            } else {
                //Рахую суму всіх транзакцій юзера. Якщо більше 100 - ставлю пропарець і більше до цього коду ніколи не повертаюсь.
                if (transactionDao.findAllDependant(siteUser.getFirst_line()) != null)
                    if (transactionDao.findAllDependant(siteUser.getFirst_line()) >= 100) {
                        residual = transactions.getAmountSpend();
                        enableFirstLine(siteUser.getFirst_line());
                        saveBonusProgramFromUser(siteUser.getFirst_line());
                    }
            }
            userService.addMoney(userService.findUser(siteUser.getFirst_line()), 0.05, residual);
        }
        //End of block for first line

        //Block for second line
        Double residualForSecond = 0.0;
        if (siteUser.getSecond_line() != null) {
            if (bonusProgramService.findByUser(siteUser.getSecond_line()).getSecondLineEnabled()) {
                residualForSecond = transactions.getAmountSpend();

            } else {
                //Дивимося, скіль потратили юзера 1 лінії, якщо сума більше 5000, значить щойно було перевищено цільовий показник.
                if ((bonusProgramService.findByUser(siteUser.getSecond_line()).getFirstLineBalance() >= 5000)) {
                    //Рахую, скільки ж дати юзеру
                    residualForSecond = bonusProgramService.findByUser(siteUser.getSecond_line()).getFirstLineBalance() - 5000;
                    enableSecondline(siteUser.getSecond_line());
                    saveBonusProgramFromUser(siteUser.getSecond_line());
                }
                //Або дивлюся суму всіз транзакцій цього юзера
                if (transactionDao.findAllDependant(siteUser.getSecond_line()) != null)
                    if (transactionDao.findAllDependant(siteUser.getSecond_line()) >= 1000) {
                        residualForSecond = bonusProgramService.findByUser(siteUser.getFirst_line()).getFirstLineBalance() - 1000;
                        enableSecondline(siteUser.getSecond_line());
                        saveBonusProgramFromUser(siteUser.getSecond_line());
                    }
            }
            userService.addMoney(userService.findUser(siteUser.getSecond_line()), 0.03, residualForSecond);
        }
        //End of block of third line

        //Block for third line
        Double residualForThird = 0.0;

        if (siteUser.getThird_line() != null) {
            if (bonusProgramService.findByUser(siteUser.getThird_line()).getThirdLineEnabled()) {
                residualForThird = transactions.getAmountSpend();
            } else {
                if ((bonusProgramService.findByUser(siteUser.getThird_line()).getSecondLineBalance() >= 10000)) {


                    residualForThird = bonusProgramService.findByUser(siteUser.getThird_line()).getSecondLineBalance() - 10000;
                    enableThirdLine(siteUser.getThird_line());
                    saveBonusProgramFromUser(siteUser.getThird_line());
                }
                else
                if (transactionDao.findAllDependant(siteUser.getThird_line()) != null)
                    if (transactionDao.findAllDependant(siteUser.getThird_line()) >= 2000) {
                        residualForThird = bonusProgramService.findByUser(siteUser.getThird_line()).getSecondLineBalance() - 2000;
                        enableThirdLine(siteUser.getThird_line());
                        saveBonusProgramFromUser(siteUser.getThird_line());
                    }
            }
            userService.addMoney(userService.findUser(siteUser.getThird_line()), 0.02, residualForThird);
        }
        //End of block for third line

        //Зберігаю транзакцію
        transactionDao.save(transactions);

    }

        public void saveBonusProgramFromUser(Long id){
            bonusProgramService.save(bonusProgramService.findByUser(id));
        }
        public void enableFirstLine(Long id){
            bonusProgramService.findByUser(id).setFirstLineEnabled(true);

        }

        public void enableSecondline(Long id){
            bonusProgramService.findByUser(id).setSecondLineEnabled(true);

        }
        public void enableThirdLine(Long id){
            bonusProgramService.findByUser(id).setThirdLineEnabled(true);
        }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //nothing here
        return null;
    }
}

