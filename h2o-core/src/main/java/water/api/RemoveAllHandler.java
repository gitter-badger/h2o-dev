package water.api;

import water.*;
import water.util.Log;

import java.util.Set;

public class RemoveAllHandler extends Handler {
  @SuppressWarnings("unused") // called through reflection by RequestServer
  public RemoveAllV3 remove(int version, RemoveAllV3 u) {
    Log.info("Removing all objects");
    Futures fs = new Futures();
    for (Job j : Job.jobs()) { j.cancel(); j.remove(fs); }
    fs.blockForPending();
    new RemoveAllTask().doAllNodes();
    Log.info("Finished removing objects");
    return u;
  }

  public class RemoveAllTask extends MRTask<RemoveAllTask> {
    @Override public byte priority() { return H2O.GUI_PRIORITY; }

    @Override public void setupLocal() {
      Log.info("Removing "+H2O.store_size()+ " objects from nodeIdx("+H2O.SELF.index()+") out of "+H2O.CLOUD.size()+" nodes.");
      H2O.clearAll();
      Log.info("Objects remaining: "+H2O.store_size());
    }
  }
}
