const content =
  'Pre-rendering?\n- 프리렌더링이 뭔데?테스트용 테스트용 테스트용 테스테스텟터세트세트스fsdfsfdsfdsfsd\n ### 글쎄? \n 이것은 테스트\n 이것은 테스트\n 이것은 테스트\n 이것은 테스트\n 이것은 테스트\n 이것은 테스트\n 이것은 테스트 ## Pre-rendering?\n## Pre-rendering?\n## Pre-rendering?\n## Pre-rendering?\n'
const DummyNotesDetail = [
  {
    noteId: 1,
    member: {
      memberId: 6,
      nickname: '지구를 지켜야 하니?',
      profileUrl: '/image/chuu.gif',
    },
    title: '지구는 어떻게 지킬까?',
    content: `${content}# 테스트 테스트 테스트\n# 테스트 테스트 테스트\n# 테스트 테스트 테스트\n# 테스트 테스트 테스트\n# 테스트 테스트 테스트\n# 테스트 테스트 테스트\n`,
    ticker: 'KRW-BTC',
    writeDate: '2024-01-20T15:00:00',
    likeCount: 4,
    liked: true,
    commentCount: 5,
  },
  {
    noteId: 2,
    member: {
      memberId: 2,
      nickname: '지구를 지켜야 하니?',
      profileUrl: '/image/chuu.gif',
    },
    title: '지구는 어떻게 지킬까?',
    content,
    ticker: 'KRW-BTC',
    writeDate: '2024-01-20T15:00:00',
    likeCount: 4,
    liked: true,
    commentCount: 5,
  },
  {
    noteId: 3,
    member: {
      memberId: 1,
      nickname: '지구를 지켜야 하니?',
      profileUrl: '/image/chuu.gif',
    },
    title: '지구는 어떻게 지킬까?',
    content,
    ticker: 'KRW-BTC',
    writeDate: '2024-01-20T15:00:00',
    likeCount: 4,
    liked: true,
    commentCount: 5,
  },
  {
    noteId: 4,
    member: {
      memberId: 3,
      nickname: '지구를 지켜야 하니?',
      profileUrl: '/image/chuu.gif',
    },
    title: '지구는 어떻게 지킬까?',
    content,
    ticker: 'KRW-BTC',
    writeDate: '2024-01-20T15:00:00',
    likeCount: 4,
    liked: true,
    commentCount: 5,
  },
  {
    noteId: 5,
    member: {
      memberId: 4,
      nickname: '지구를 지켜야 하니?',
      profileUrl: '/image/chuu.gif',
    },
    title: '지구는 어떻게 지킬까?',
    content,
    ticker: 'KRW-BTC',
    writeDate: '2024-01-20T15:00:00',
    likeCount: 4,
    liked: true,
    commentCount: 5,
  },
]

export default DummyNotesDetail
