package partnership_program.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import partnership_program.dao.UserDao;
import partnership_program.models.BonusProgram;
import partnership_program.models.SiteUser;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private BonusProgramService bonusProgramService;

    public void save(SiteUser user){

        //Побудова каркасу юзерів 1-ї, 2-ї і 3-ї лінії
        Long userForSecondLine;
        if (user.getFirst_line() != null) {
            if (findUser(user.getFirst_line()).getFirst_line() != null) {
                userForSecondLine = findUser(user.getFirst_line()).getId();
                System.out.println(userForSecondLine);
                System.out.println(findUser(userForSecondLine).getFirst_line());
                if (findUser(userForSecondLine).getFirst_line() != null)
                    user.setSecond_line(findUser(userForSecondLine).getFirst_line());
                if (findUser(userForSecondLine).getSecond_line() != null)
                    user.setThird_line(findUser(userForSecondLine).getSecond_line());
            }
        }


        userDao.save(user);
        if (bonusProgramService.findByUser(user.getId()) == null){
            BonusProgram bonusProgram = new BonusProgram();
            bonusProgram.setUser(user.getId());
            bonusProgramService.save(bonusProgram);
        }

    }
    public void save(SiteUser ... users){
        for (SiteUser user : users){
            userDao.save(user);
        }
    }
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }

    public SiteUser findUser(Long id){
        return userDao.findSiteUserById(id);
    }

    public List<SiteUser> findUser(Long ... id){
        List<SiteUser> list = new ArrayList<>();
        for (Long user : id)
            list.add(userDao.findSiteUserById(user));
        return list;
    }

    public void addMoney(SiteUser siteUser, Double index, Double money){
        siteUser.addBalance(index*money);
        userDao.save(siteUser);
    }
}

