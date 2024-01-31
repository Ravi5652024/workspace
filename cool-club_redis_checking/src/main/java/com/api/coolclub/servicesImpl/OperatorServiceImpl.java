package com.api.coolclub.servicesImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.coolclub.config.GeneralConfig;
import com.api.coolclub.entities.OperatorConfig;
import com.api.coolclub.models.pojo.MaxOperatorUuidPojo;
import com.api.coolclub.models.request.AddOperator;
import com.api.coolclub.models.request.UpdateOperatorReq;
import com.api.coolclub.models.response.GenericRes;
import com.api.coolclub.models.response.GetAllOperatorsRes;
import com.api.coolclub.repository.OperatorConfigRepo;
import com.api.coolclub.services.OperatorService;
import com.api.coolclub.utils.Constants;
import com.api.coolclub.utils.EncDecPasswordUtil;
import com.google.gson.Gson;

/*
 * @Author Rohan_Sharma
*/


@Service
public class OperatorServiceImpl implements OperatorService {
    private static final Logger log = LoggerFactory.getLogger(OperatorServiceImpl.class);

    private final Gson gson;
    private final OperatorConfigRepo operatorConfigRepo;
    public OperatorServiceImpl(Gson gson, OperatorConfigRepo operatorConfigRepo){
        this.gson = gson;
        this.operatorConfigRepo = operatorConfigRepo;
    }

    
    /* ---------------------------------- ADD OPERATOR ----------------------------------- */
    @Override
    public ResponseEntity<GenericRes<String>> addOperator(AddOperator operatorData) {
        try {
            if(hasInvalidParameters(operatorData)){
                log.warn("-- [addOperator]: invalid parameter");
                String msg = GeneralConfig.globalizedMessages.get(Constants.INVALID_PARAMETERS).get("en");
                return new ResponseEntity<>(new GenericRes<>(Constants.BAD_REQUEST_CODE, Constants.UNSUCCESSFUL, msg), HttpStatus.BAD_REQUEST);
            }

            long count = operatorConfigRepo.countByOperatorAndCountry(operatorData.getOperator(), operatorData.getCountry());
            log.debug("-- [addOperator]: findUser: {}",count);
            if(count != 0 ){
                log.warn("-- [addOperator]: name/country already present");
                String msg = GeneralConfig.globalizedMessages.get(Constants.OPERATOR_EXIST).get("en");
                return new ResponseEntity<>(new GenericRes<>(Constants.CONFLICT_CODE, Constants.UNSUCCESSFUL, msg), HttpStatus.CONFLICT);
            }

            //-- getting value of uuid
            Optional<MaxOperatorUuidPojo> optionalMaxUuid = operatorConfigRepo.findMaxUuid();
            Integer maxUuid = optionalMaxUuid.map(MaxOperatorUuidPojo::maxUuid).orElse(null);
            maxUuid = maxUuid==null?0:maxUuid+1;
            
            String encPassword = EncDecPasswordUtil.encrypt(operatorData.getCmsPassword());

            OperatorConfig entity = new OperatorConfig();
            BeanUtils.copyProperties(entity, operatorData);
            entity.setCmsPassword(encPassword);
            entity.setUuid(maxUuid);

            operatorConfigRepo.save(entity);

            String msg = GeneralConfig.globalizedMessages.get(Constants.OPERATOR_ADD_SUCCESSFULY).get("en");
            return new ResponseEntity<>(new GenericRes<>(Constants.SUCCESS_CODE, Constants.SUCCESSFUL, msg), HttpStatus.OK);
        } catch (Exception e) {
            log.error("-- [addOperator] : ERROR",e);
            String msg = GeneralConfig.globalizedMessages.get(Constants.SERVER_ERROR).get("en");
            return new ResponseEntity<>(new GenericRes<>(Constants.INTERNAL_SERVER_ERROR_CODE, Constants.UNSUCCESSFUL, msg), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean hasInvalidParameters(AddOperator operatorData) {
        return Stream.of(operatorData.getOperator(), operatorData.getCountry(),operatorData.getDefaultLanguage(), operatorData.getCmsUsername(), operatorData.getCmsPassword()).anyMatch(Objects::isNull) || operatorData.getLanguageSupport().isEmpty();
    }

    /* ---------------------------------- GET ALL OPERATOR ----------------------------------- */
    @Override
    public ResponseEntity<GenericRes<List<GetAllOperatorsRes>>> getAllOperator() {
        try {
            List<OperatorConfig> allOperators = operatorConfigRepo.findAll();
            List<GetAllOperatorsRes> result = new ArrayList<>();

            for (OperatorConfig val : allOperators) {
                GetAllOperatorsRes response = new GetAllOperatorsRes();
                BeanUtils.copyProperties(response, val);
                result.add(response);
            }

            return new ResponseEntity<>(new GenericRes<>(Constants.SUCCESS_CODE, Constants.SUCCESSFUL,null, result), HttpStatus.OK);
        } catch (Exception e) {
        log.error("-- [getAllOperator]: ERROR",e);
            String msg = GeneralConfig.globalizedMessages.get(Constants.SERVER_ERROR).get("en");
            return new ResponseEntity<>(new GenericRes<>(Constants.INTERNAL_SERVER_ERROR_CODE, Constants.UNSUCCESSFUL, msg), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* ---------------------------------- UPDATE OPERATOR ----------------------------------- */
    @Override
    public ResponseEntity<GenericRes<String>> updateOperator(UpdateOperatorReq operatorData) {
        try {
            if(operatorData.getUuid() == null){
                String msg = GeneralConfig.globalizedMessages.get(Constants.INVALID_PARAMETERS).get("en");
                return new ResponseEntity<>(new GenericRes<>(Constants.BAD_REQUEST_CODE, Constants.UNSUCCESSFUL, msg), HttpStatus.BAD_REQUEST);
            }

            if(operatorData.getCmsPassword() != null){
                String encPassword = EncDecPasswordUtil.encrypt(operatorData.getCmsPassword());
                operatorData.setCmsPassword(encPassword);
            }

            Optional<OperatorConfig> operatorConfigData = operatorConfigRepo.findByUuid(operatorData.getUuid());

            operatorConfigData.ifPresent(data -> {
                Optional.ofNullable(operatorData.getName()).ifPresent(data::setOperator);
                Optional.ofNullable(operatorData.getCountry()).ifPresent(data::setCountry);
                Optional.ofNullable(operatorData.getDefaultLanguage()).ifPresent(data::setDefaultLanguage);
                Optional.ofNullable(operatorData.getLanguageSupport()).ifPresent(data::setLanguageSupport);
                Optional.ofNullable(operatorData.getCmsUsername()).ifPresent(data::setCmsUsername);
                Optional.ofNullable(operatorData.getCmsPassword()).ifPresent(data::setCmsPassword);

                operatorConfigRepo.save(data); // Save the updated entity
            });

            if(!operatorConfigData.isPresent()){
                String msg = GeneralConfig.globalizedMessages.get(Constants.INVALID_PARAMETERS).get("en");
                return new ResponseEntity<>(new GenericRes<>(Constants.BAD_REQUEST_CODE, Constants.UNSUCCESSFUL, msg), HttpStatus.BAD_REQUEST);
            }

            String msg = GeneralConfig.globalizedMessages.get(Constants.OPERATOR_UPDATED_SUCCESSFULY).get("en");
            return new ResponseEntity<>(new GenericRes<>(Constants.SUCCESS_CODE, Constants.SUCCESSFUL, msg), HttpStatus.OK);
        } catch (Exception e) {
            String msg = GeneralConfig.globalizedMessages.get(Constants.SERVER_ERROR).get("en");
            return new ResponseEntity<>(new GenericRes<>(Constants.INTERNAL_SERVER_ERROR_CODE, Constants.UNSUCCESSFUL, msg), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
