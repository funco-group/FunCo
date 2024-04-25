import FollowingModal from "../TradeHistoryPage/FollowPage/FollowingPage/FollowingModal";
import PortfolioAsset from "./PortfolioAsset";
import PortfolioAssetChange from "./PortfolioAssetChange";

interface PortfolioModalProps {
  memberId: number;
  nickname: string;
  handlePortFolioClick: () => void;
}

function PortfolioModal({
  memberId,
  nickname,
  handlePortFolioClick,
}: PortfolioModalProps) {
  return (
    <FollowingModal
      title={`${nickname} 님의 포트폴리오`}
      handleClick={handlePortFolioClick}
    >
      <PortfolioAsset memberId={memberId} />
      <PortfolioAssetChange memberId={memberId} />
    </FollowingModal>
  );
}

export default PortfolioModal;
