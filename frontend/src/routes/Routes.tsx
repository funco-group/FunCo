import { Navigate, Route, Routes } from "react-router-dom";
import Header from "@/pages/base/Header";
import HomePage from "@/pages/HomePage";
import CryptoPage from "@/pages/CryptoPage";
import UserPage from "@/pages/UserPage";
import History from "@/pages/TradeHistoryPage";
import Asset from "@/pages/TradeHistoryPage/AssetPage";
import Result from "@/pages/TradeHistoryPage/ReturnResultPage";
import ChangeHistory from "@/pages/TradeHistoryPage/AssetChangePage";
import Follow from "@/pages/TradeHistoryPage/FollowPage";
import Following from "@/pages/TradeHistoryPage/FollowPage/FollowingPage";
import Follower from "@/pages/TradeHistoryPage/FollowPage/FollowerPage";
import OpenOrders from "@/pages/TradeHistoryPage/OpenOrdersPage";
import Rank from "@/pages/RankPage";
import NarrowLayout from "@/components/layout/NarrowLayout";
import WideLayout from "@/components/layout/WideLayout";
import Redirect from "@/pages/base/Redirect";

export default function RoutesComponent() {
  return (
    <Routes>
      <Route path="/redirect" element={<Redirect />} />
      <Route path="/" element={<Header />}>
        <Route index element={<HomePage />} />
        <Route
          path="trade/:coinCode"
          element={
            <WideLayout>
              <CryptoPage />
            </WideLayout>
          }
        />
        <Route
          path="member/:memberId"
          element={
            <NarrowLayout>
              <UserPage />
            </NarrowLayout>
          }
        />
        <Route
          path="history"
          element={
            <WideLayout>
              <History />
            </WideLayout>
          }
        >
          <Route index element={<Navigate to="asset" />} />
          <Route path="asset" element={<Asset />} />
          <Route path="result" element={<Result />} />
          <Route path="change" element={<ChangeHistory />} />
          <Route path="follow" element={<Follow />}>
            <Route index element={<Navigate to="following" />} />
            <Route path="following" element={<Following />} />
            <Route path="follower" element={<Follower />} />
          </Route>
          <Route path="orders" element={<OpenOrders />} />
        </Route>
        <Route
          path="rank"
          element={
            <NarrowLayout>
              <Rank />
            </NarrowLayout>
          }
        />
      </Route>
      <Route path="*" element={<div>Not Found</div>} />
    </Routes>
  );
}
