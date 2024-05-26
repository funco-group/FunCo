import UserPageContainer from '@/containers/UserPageContainer'

function UserPage({ params }: { params: { memberId: number } }) {
  const { memberId } = params
  return <UserPageContainer memberId={memberId} />
}

export default UserPage
