package com.bnzy.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bnzy.domian.Recording;
import com.bnzy.mapper.RecordingMapper;
import com.bnzy.service.RecordingService;
import org.springframework.stereotype.Service;

@Service
public class RecordingServiceImp extends ServiceImpl<RecordingMapper, Recording> implements RecordingService {
}
