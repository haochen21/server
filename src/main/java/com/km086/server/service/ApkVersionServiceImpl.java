package com.km086.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.km086.server.model.apk.ApkVersion;
import com.km086.server.repository.apk.ApkVersionRepository;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class ApkVersionServiceImpl implements ApkVersionService {

    @Autowired
    ApkVersionRepository apkVersionRepository;

    @Override
    public ApkVersion findById(Long id) {
        return apkVersionRepository.findById(id).get();
    }

}
