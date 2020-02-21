package Controller;

import Model.State.PrgState;
import Model.Values.RefValue;
import Model.Values.Value;
import Repository.IRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Controller {
    private IRepository repo;
    private ExecutorService executor;

    public Controller(IRepository repo){
        this.repo = repo;
    }

//    private Map<Integer, Value> unsafeGarbageCollector(List<Integer> symTbl, Map<Integer, Value> heap){
//        return heap.entrySet().stream()
//                .filter(e->symTbl.contains(e.getKey()))
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//    }

//    private Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer,Value>
//            heap){
//        Map<Integer,Value> map= heap.entrySet().stream()
//                .filter(e->symTableAddr.contains(e.getKey()))
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//
//        Map<Integer,Value> copy = new HashMap<>(map);
//        for(Map.Entry<Integer,Value> entry : copy.entrySet()){
//            Value val = entry.getValue();
//            while(val instanceof RefValue){
//                RefValue ref=(RefValue) val;
//                Integer addr=ref.getAddress();
//                Value aux=heap.get(addr);
//                map.put(addr,aux);
//                val=aux;
//            }
//        }
//        return map;
//    }
//

    private Map<Integer, Value> conservativeGarbageCollector(List<Integer> symTableAddr, Map<Integer,Value>
            heap){
        Map<Integer,Value> map= heap.entrySet().stream()
                .filter(e->symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Map<Integer,Value> copy = new HashMap<>(map);
        for(Map.Entry<Integer,Value> entry : copy.entrySet()){
            Value val = entry.getValue();
            while(val instanceof RefValue){
                RefValue ref=(RefValue) val;
                Integer addr=ref.getAddress();
                Value aux=heap.get(addr);
                map.put(addr,aux);
                val=aux;
            }
        }
        return map;
    }

    private List<Integer> getAddrFromSymTable(Collection<Value> sym){
        return sym.stream()
                .filter(v->v instanceof RefValue)
                .map(v->{RefValue v1 = (RefValue)v; return  v1.getAddress();})
                .collect(Collectors.toList());
    }

    private List<PrgState> removeCompletedPrg(List<PrgState> inPrgList){
        return inPrgList.stream().filter(PrgState::isNotCompleted).collect(Collectors.toList());
    }

//    public void allStep() throws MyException, MyStmtExecException, IOException {
//        PrgState prg = repo.getCrtPrg();
//        System.out.println(prg.toString());
//        repo.logPrgStateExec();
//        while(!prg.getStk().isEmpty()){
//            oneStep(prg);
//            repo.logPrgStateExec();
//            //prg.getHeap().setContent(unsafeGarbageCollector(getAddrFromSymTable(prg.getSymTable().getContent().values()),prg.getHeap().getContent()));
//            prg.getHeap().setContent(safeGarbageCollector(getAddrFromSymTable(prg.getSymTable().getContent().values()),prg.getHeap().getContent()));
//            repo.logPrgStateExec();
//            //System.out.println(prg.toString());
//        }
//    }

    public ObservableList<Integer> getIDs(){
        ObservableList<Integer> data = FXCollections.observableArrayList();
        for (PrgState p: repo.getPrgList()) {
            data.add(p.getId());
        }
        return data;
    }

    public PrgState getPrgState(int index){
        if(repo.getPrgList().isEmpty())
            return null;
        return repo.getPrgList().get(index);
    }

    public void addPrg(PrgState prg){
        repo.addPrg(prg);
    }

    private void oneStepForAllPrg(List<PrgState> states) throws InterruptedException {
        states.forEach(p->repo.logPrgStateExec(p));
        List<Callable<PrgState>> callList = states.stream()
                .map((PrgState p)->(Callable<PrgState>)(p::oneStep))
                .collect(Collectors.toList());
        List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                    .map(future -> {try{
                        return future.get();
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                        return null;
                    }
                    })
                    .filter(p->p!=null)
                    .collect(Collectors.toList());
        states.addAll(newPrgList);
        states.forEach(prg->repo.logPrgStateExec(prg));
        repo.setPrgList(states);
    }

    public void allStep(){
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());
        while (prgList.size() > 0){
            prgList.forEach(e->e.getHeap().setContent(conservativeGarbageCollector(getAddrFromSymTable(e.getSymTable().getContent().values()), e.getHeap().getContent())));
            try{
                oneStepForAllPrg(prgList);
            }catch(InterruptedException e){
                System.out.println(e.getMessage());
                return;
            }
            prgList = removeCompletedPrg(repo.getPrgList());
        }
        executor.shutdownNow();
        repo.setPrgList(prgList);
    }

    public void oneStep()throws InterruptedException{
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());
        prgList.forEach(e->e.getHeap().setContent(conservativeGarbageCollector(getAddrFromSymTable(e.getSymTable().getContent().values()), e.getHeap().getContent())));
        System.out.println(prgList);
        oneStepForAllPrg(prgList);
        //prgList = removeCompletedPrg(repo.getPrgList());
        executor.shutdownNow();
        repo.setPrgList(prgList);
    }
}
