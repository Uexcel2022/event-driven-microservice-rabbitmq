package com.eazybytes.loan.service.impl;

import com.eazybytes.loan.exception.ResourceNotFoundException;
import com.eazybytes.loan.repository.LoanRepository;
import com.eazybytes.loan.constants.LoanConstants;
import com.eazybytes.loan.dto.LoanDto;
import com.eazybytes.loan.dto.ResponseDto;
import com.eazybytes.loan.entity.Loan;
import com.eazybytes.loan.exception.MobileNumberExistException;
import com.eazybytes.loan.mapper.LoanMapper;
import com.eazybytes.loan.service.ILoanService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
@Transactional
public class LoanServiceServiceImpl implements ILoanService {
    private final LoanRepository loanRepository;
    /**
     * @param mobileNumber - A mobil number to create new loan details
     * @return  - Returns response code and message with ResponseDto objection
     */
    @Override
    public ResponseDto createLoanDetails(String mobileNumber) {
        if(loanRepository.existsByMobileNumber(mobileNumber)) {
            throw new MobileNumberExistException(mobileNumber);
        }
        loanRepository.save(LoanMapper.mapToNewLoan(mobileNumber,new Loan()));
        return new ResponseDto(LoanConstants.STATUS_CODE_201,LoanConstants.MESSAGE_201);
    }

    /**
     * @param mobileOrLoanNumber - A request parameter to hold mobile or loan number
     * @return Returns loan information with LoanDto object
     */
    @Override
    public LoanDto fetchLoanDetails(String mobileOrLoanNumber) {
        return LoanMapper.mapToLoanDto(loanExists(mobileOrLoanNumber),new LoanDto());
    }

    /**
     * @param loanDto - A request body to hold information to be updated
     * @return Returns boolean value indicating update is successful or not
     */
    @Override
    public boolean updateLoanDetails(LoanDto loanDto) {
        Loan loan = loanExists(loanDto.getLoanNumber());
         loanRepository.save(LoanMapper.mapToLoanUpdate(loanDto,loan));
        return true;
    }

    /**
     * @param mobileOrLoanNumber - A request parameter hold mobile or loan number
     * @return - Return boolean value indicating update is successful or not
     */
    @Override
    public boolean deleteLoanDetails(String mobileOrLoanNumber) {
        loanRepository.delete(loanExists(mobileOrLoanNumber));
        return true;
    }


    private Loan loanExists(String mobileOrLoanNumber) {

        return  loanRepository.findByLoanNumberOrMobileNumber(
                mobileOrLoanNumber,mobileOrLoanNumber)
                .orElseThrow(() -> {
                    if(mobileOrLoanNumber.length()==11) {
                       return new ResourceNotFoundException(
                                "Loan", "mobileNumber", mobileOrLoanNumber);
                    }else {
                      return   new ResourceNotFoundException(
                                "Loan", "loanNumber", mobileOrLoanNumber);
                    }
                });
    }


}
