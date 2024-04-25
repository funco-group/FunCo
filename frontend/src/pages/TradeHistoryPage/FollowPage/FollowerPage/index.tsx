import { useState } from "react";
import FollowerTab from "./FollowerTab";
import FollowerContentTable from "./FollowerContentTable";

function Index() {
  const [nowTabName, setNowTabName] = useState<"all" | "following" | "settled">(
    "all",
  );
  return (
    <div>
      <FollowerTab nowTabName={nowTabName} setNowTabName={setNowTabName} />
      <FollowerContentTable nowTabName={nowTabName} />
    </div>
  );
}

export default Index;
