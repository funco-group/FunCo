INSERT INTO Member (
    oauth_server_id,
    oauth_server,
    nickname,
    profile_url,
    introduction,
    cash,
    status,
    portfolio_status,
    portfolio_price,
    badge_yn
) VALUES (
             '12345', -- Replace '12345' with actual OAuth Server ID
             'GOOGLE', -- Replace 'GOOGLE' with the actual OAuth Server Type (e.g., GOOGLE, FACEBOOK, etc.)
             'JohnDoe', -- Replace 'JohnDoe' with the actual nickname
             'http://example.com/johndoe.jpg', -- Replace with actual profile URL
             'I love coding and coffee.', -- Replace with actual introduction text
             50000, -- Replace 50000 with actual available cash amount
             'NORMAL', -- Replace 'NORMAL' with actual MemberStatus ('NORMAL', 'ADMIN', 'WITHDRAW')
             'PUBLIC', -- Replace 'PUBLIC' with actual PortfolioStatusType ('PUBLIC', 'PRIVATE')
             150000, -- Replace 150000 with actual portfolio price
             TRUE -- Replace TRUE with actual badge wearing status (TRUE or FALSE)
         );
