package project.sd.server.services;

import project.sd.server.dto.BorrowDto;

import java.util.List;

public interface BorrowService {

    void deleteBorrowByBookId(Integer id);

    List<BorrowDto> viewBorrowsByBookIdAndAccount(String username, Integer id);

    List<BorrowDto> viewBorrowsByAccount(String username);
}
