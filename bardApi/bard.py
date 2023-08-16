from flask import Flask, request, jsonify
import bardapi

app = Flask(__name__)

@app.route('/askBard', methods=['POST'])
def get_answer():
    try:
        # Get input_text from request JSON
        data = request.get_json()
        input_text = data.get('input_text')

        if not input_text:
            return jsonify({'error': 'Invalid input data'}), 400

        # Set your token here
        token = '' 

        # Send an API request and get a response.
        response = bardapi.core.Bard(token).get_answer(input_text)

        # Convert sets to lists before JSON serialization
        response['images'] = list(response['images'])
        response['factuality_queries'] = list(response['factuality_queries']) if response['factuality_queries'] else None

        return jsonify(response)

    except Exception as e:
        return jsonify({'error': str(e)}), 500

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=2000)
