package Repository;

import Model.Exceptions.MyException;
import Model.State.PrgState;

import java.io.IOException;
import java.util.List;

public interface IRepository {
    void logPrgStateExec(PrgState prgState);
    void addPrg(PrgState prg);
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> states);
}
