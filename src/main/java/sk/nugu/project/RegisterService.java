package sk.nugu.project;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.nugu.mapper.RegisterMapper;

@Service
@Slf4j
public class RegisterService {
    private final RegisterMapper registerMapper;

    @Autowired
    public RegisterService(final RegisterMapper registerMapper) {
        this.registerMapper = registerMapper;
    }

    public void saveSggNameInRegistration(final String sggName){
        registerMapper.saveSggNameInRegistration(sggName);
    }
}
