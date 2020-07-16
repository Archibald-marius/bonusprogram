package partnership_program.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import partnership_program.dao.BonusDao;
import partnership_program.models.BonusProgram;
import partnership_program.models.SiteUser;

import java.util.ArrayList;
import java.util.List;

@Service
public class BonusProgramService {

    @Autowired
    private BonusDao bonusDao;

    @Autowired
    private UserService userService;

    public void save(BonusProgram bonusProgram){
        bonusDao.save(bonusProgram);
    }

    public void save(List<BonusProgram> bonusPrograms){
        for (BonusProgram bp : bonusPrograms)
            bonusDao.save(bp);
    }

    public BonusProgram findByUser(Long user){
        return bonusDao.findByUser(user);
    }

    public List<BonusProgram> findByLinesUser(SiteUser siteUser){
        List<BonusProgram> list = new ArrayList<>();
            if (siteUser.getFirst_line()!= null)
            list.add(bonusDao.findByUser(siteUser.getFirst_line()));
        if (siteUser.getSecond_line()!= null)
            list.add(bonusDao.findByUser(siteUser.getSecond_line()));
        if (siteUser.getThird_line()!= null)
            list.add(bonusDao.findByUser(siteUser.getThird_line()));
        return list;
    }

}

