document.addEventListener("DOMContentLoaded", function () {
    // Load danh sách main topics
    fetch("/api/chatbot/topics")
        .then(response => response.json())
        .then(data => {
            const mainTopicsTable = document.querySelector('#mainTopics');
            data.forEach(topic => {
                console.log(topic.id);
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${topic.id}</td>
                    <td id="topic_name-${topic.id}">${topic.topic_name}</td>
                    <td>
                        <button id="viewButton-${topic.id}" onclick="viewDetails(${topic.id})">View</button>
                        <button id="editButton-${topic.id}" onclick="editDetails(${topic.id})">Edit</button>
                        <button id="deleteButton-${topic.id}" onclick="deleteTopic(${topic.id})">Delete</button>
                        <button id="saveButton-${topic.id}" onclick="saveDetails(${topic.id})" style="display: none;">Save</button>
                    </td>
                `;
                mainTopicsTable.appendChild(row);
            });
        })
        .catch(error => console.error('Error fetching main topics:', error));
});

function viewDetails(id) {
    const existingDetailsDiv = document.getElementById(`detailsDiv-${id}`);

    if (existingDetailsDiv) {
        existingDetailsDiv.remove();
        return;
    }

    fetch(`/api/chatbot/view/${id}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            const subTopicsContainer = document.getElementById('subTopicsContainer');
            const newDetailsDiv = document.createElement('div');
            newDetailsDiv.id = `detailsDiv-${id}`;
            newDetailsDiv.className = 'chatbot-settings-container';

            console.log(data.type);
            if (data.type === "topics") {
                newDetailsDiv.innerHTML = `
                    <div class="table-header">
                        <h3>Sub-Topics for Topic ID: ${id}</h3>
                        <button onclick="deleteDetails(${id})">Close</button>
                    </div>
                    <table id="detailsTable-${id}">
                        <thead class="text-primary">
                        <tr>
                            <th>TOPIC ID</th>
                            <th>TOPIC NAME</th>
                            <th>ACTIONS</th>
                        </tr>
                        </thead>
                        <tbody id="subTopicsOrQuestions-${id}">
                        </tbody>
                    </table>
                `;
            } else if (data.type === "questions") {
                newDetailsDiv.innerHTML = `
                    <div class="table-header">
                        <h3>Question List for Topic ID: ${id}</h3>
                        <button onclick="deleteDetails(${id})">Close</button>
                    </div>
                    <table id="detailsTable-${id}">
                        <thead class="text-primary">
                        <tr>
                            <th>QUESTION ID</th>
                            <th>QUESTION</th>
                            <th>ANSWER</th>
                            <th>ACTIONS</th>
                        </tr>
                        </thead>
                        <tbody id="subTopicsOrQuestions-${id}">
                        </tbody>
                    </table>
                `;
            }

            subTopicsContainer.appendChild(newDetailsDiv);

            const detailsTable = document.querySelector(`#subTopicsOrQuestions-${id}`);

            if (data.type === "topics") {
                data.data.forEach(subTopic => {
                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td>${subTopic.id}</td>
                        <td id="topic_name-${subTopic.id}">${subTopic.topic_name}</td>
                        <td>
                            <button id="viewButton-${subTopic.id}" onclick="viewDetails(${subTopic.id})">View</button>
                            <button id="editButton-${subTopic.id}" onclick="editDetails(${subTopic.id})">Edit</button>
                            <button id="deleteButton-${subTopic.id}" onclick="deleteTopic(${subTopic.id})">Delete</button>
                            <button id="saveButton-${subTopic.id}" style="display:none;" onclick="saveDetails(${subTopic.id})">Save</button>
                        </td>
                    `;
                    detailsTable.appendChild(row);
                });
            } else if (data.type === "questions") {
                data.data.forEach(question => {
                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td>${question.id}</td>
                        <td id="questionText-${question.id}">${question.question}</td>
                        <td id="answerText-${question.id}">${question.answer}</td>
                        <td>
                            <button id="editButtonQuestion-${question.id}" onclick="editQuestion(${question.id})">Edit</button>
                            <button id="deleteButtonQuestion-${question.id}" onclick="deleteQuestion(${question.id})">Delete</button>
                            <button id="saveButtonQuestion-${question.id}" style="display:none;" onclick="saveQuestion(${question.id})">Save</button>
                        </td>
                    `;
                    detailsTable.appendChild(row);
                });
            }

            newDetailsDiv.style.display = 'block';
        })
        .catch(error => console.error('Error fetching details:', error));
}

// Hàm chỉnh sửa topic name
function editDetails(id) {
    const topic_nameCell = document.getElementById(`topic_name-${id}`);
    const currentName = topic_nameCell.textContent;
    topic_nameCell.innerHTML = `<textarea id="editInput-${id}" class="editable-textarea">${currentName}</textarea>`;

    // Hiện nút Save, vô hiệu hóa nút Edit và View
    document.getElementById(`editButton-${id}`).style.display = 'none';
    document.getElementById(`viewButton-${id}`).disabled = true;
    document.getElementById(`saveButton-${id}`).style.display = 'inline';
}

// Hàm lưu thay đổi topic name
function saveDetails(id) {
    const newName = document.getElementById(`editInput-${id}`).value;

    console.log(newName);

    fetch(`/api/chatbot/topics/update/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ topic_name: newName })
    })
        .then(response => {
            if (response.ok) {
                // Cập nhật giao diện với tên mới
                const topic_nameCell = document.getElementById(`topic_name-${id}`);
                topic_nameCell.textContent = newName;  // Hiển thị giá trị mới dưới dạng văn bản, không còn là input nữa

                // Khôi phục trạng thái của các nút
                document.getElementById(`editButton-${id}`).style.display = 'inline';
                document.getElementById(`viewButton-${id}`).disabled = false;
                document.getElementById(`saveButton-${id}`).style.display = 'none';
            } else {
                console.error('Error saving topic name');
            }
        })
        .catch(error => console.error('Error saving topic name:', error));
}

function editQuestion(id) {
    // Lấy các ô chứa nội dung của câu hỏi và câu trả lời
    const questionTextCell = document.getElementById(`questionText-${id}`);
    const answerTextCell = document.getElementById(`answerText-${id}`);
    const currentQuestion = questionTextCell.textContent;
    const currentAnswer = answerTextCell.textContent;

    // Tạo các ô input để người dùng nhập giá trị mới cho câu hỏi và câu trả lời
    // Tạo các ô textarea để người dùng nhập giá trị mới cho câu hỏi và câu trả lời
    questionTextCell.innerHTML = `<textarea id="editQuestionInput-${id}" class="editable-textarea">${currentQuestion}</textarea>`;
    answerTextCell.innerHTML = `<textarea id="editAnswerInput-${id}" class="editable-textarea">${currentAnswer}</textarea>`;

    // Ẩn nút Edit và hiện nút Save
    document.getElementById(`editButtonQuestion-${id}`).style.display = 'none';
    document.getElementById(`saveButtonQuestion-${id}`).style.display = 'inline';
}

function saveQuestion(id) {
    // Lấy giá trị mới từ các ô input
    const questionInput = document.getElementById(`editQuestionInput-${id}`);
    const answerInput = document.getElementById(`editAnswerInput-${id}`);

    // Kiểm tra nếu không tìm thấy phần tử
    if (!questionInput || !answerInput) {
        console.error('Không tìm thấy phần tử textarea để lấy giá trị.');
        return;
    }

    const newQuestion = questionInput.value;
    const newAnswer = answerInput.value;

    // Gửi yêu cầu cập nhật lên server
    fetch(`/api/chatbot/questions/update/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ question: newQuestion, answer: newAnswer })
    })
        .then(response => {
            if (response.ok) {
                // Cập nhật giao diện với giá trị mới sau khi cập nhật thành công
                document.getElementById(`questionText-${id}`).textContent = newQuestion;
                document.getElementById(`answerText-${id}`).textContent = newAnswer;

                // Khôi phục trạng thái của các nút
                document.getElementById(`editButtonQuestion-${id}`).style.display = 'inline';
                document.getElementById(`saveButtonQuestion-${id}`).style.display = 'none';
            } else {
                console.error('Error saving question');
            }
        })
        .catch(error => console.error('Error saving question:', error));
}

function deleteDetails(id) {
    const detailsDiv = document.getElementById(`detailsDiv-${id}`);
    if (detailsDiv) {
        detailsDiv.remove();
    }
}

function deleteTopic(id) {
    fetch(`/api/chatbot/topics/delete/${id}`, {
        method: 'DELETE',
    })
        .then(response => {
            if (response.ok) {
                // Xóa hàng của topic khỏi bảng
                const row = document.getElementById(`deleteButton-${id}`).closest('tr');
                row.remove();
                console.log("Topic deleted successfully");
            } else {
                console.error("Failed to delete topic");
            }
        })
        .catch(error => console.error("Error deleting topic:", error));
}

function deleteQuestion(id) {
    fetch(`/api/chatbot/questions/delete/${id}`, {
        method: 'DELETE',
    })
        .then(response => {
            if (response.ok) {
                // Xóa hàng của question khỏi bảng
                const row = document.getElementById(`deleteButtonQuestion-${id}`).closest('tr');
                row.remove();
                console.log("Question deleted successfully");
            } else {
                console.error("Failed to delete question");
            }
        })
        .catch(error => console.error("Error deleting question:", error));
}
