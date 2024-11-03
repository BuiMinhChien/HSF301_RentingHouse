function applyHouseSearch() {
    // Lấy trạng thái đã chọn (Free hoặc Busy)
    const selectedStatus = $('input[name="status"]:checked').val();

    // Lấy tên của Tỉnh, Quận và Phường thay vì ID
    const province = $('#tinh option:selected').text();
    const district = $('#quan option:selected').text();
    const ward = $('#phuong option:selected').text();

    console.log(province);
    // Gửi yêu cầu AJAX để lấy danh sách nhà đã lọc
    $.ajax({
        url: '/customer/filter_houses', // URL của controller xử lý lọc và tìm kiếm
        type: 'GET',
        data: {
            status: selectedStatus, // Truyền trạng thái đã chọn
            province: province,
            district: district,
            ward: ward
        },
        success: function (response) {
            // Cập nhật danh sách nhà trong phần tử có id 'auctionListContainer'
            $('#auctionListContainer').html($(response).find('#auctionListContainer').html());
        },
        error: function (xhr, status, error) {
            console.error('Error occurred:', error);
        }
    });
}

function clearSearchAuctionsFilters() {
    // Xóa giá trị trong form tìm kiếm
    document.getElementById("searchForm").reset();
    applyHouseSearch();
}

function clearLocationFilters() {
    // Xóa giá trị trong form Tỉnh, Quận, Phường
    document.getElementById("locationFilterForm").reset();
    applyHouseSearch();
}
