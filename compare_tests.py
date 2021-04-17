import os

def file_read(file):
    tartalom = ""
    with open(file, 'r') as f:
        for line in f:
            tartalom += line
    return tartalom

def compare_str(str1, str2):
    str1 = str1.rstrip()
    str2 = str2.rstrip()
    if (len(str1) != len(str2)): return False
    for i in range(len(str1)):
        if str1[i] != str2[i]:
            return False
    return True

def cut_the_end(str1):
    str2 = ''
    for i in range(len(str1)-1, 0, -1):
        if str1[i] != '_':
            str2 = str1[i] + str2
        else: break
    return str2


def main():
    os.system("javac src/*.java -d bin")

    input_files_path = os.getcwd()+ '\\' + 'input_files'
    input_files = []
    for root, dirs, files in os.walk(input_files_path):
        input_files = files


    try:
        os.remove('de_facto_output_files')
        os.mkdir('de_facto_output_files')
    except: pass
    
    de_facto_output_files_path = os.getcwd()+ '\\' + 'de_facto_output_files'
    for i in range(len(input_files)):
        sor = "java -cp bin Skeleton " + input_files_path + '\\' + input_files[i] + ' ' + de_facto_output_files_path + '\\' + 'output_' + cut_the_end(input_files[i])
        os.system(sor)

    output_files_path = os.getcwd()+ '\\' + 'output_files'
    output_files = []
    for root, dirs, files in os.walk(output_files):
        output_files = files

    print("The program finised with running the tests, you can find them in the 'de_facto_output_files' directory.")
    
    out = ""
    if (len(output_files) != len(de_facto_output_files)): print("The number of the expected and the actual test files are different.")
    else:
        output_files.sort()
        de_facto_output_files.sort()
        for i in range(len(output_files)):
            file1 = file_read(output_files_path + '\\' + output_files[i])
            file2 = file_read(de_facto_output_files_path + '\\' + de_facto_output_files[i])
            if compare_str(file1, file2): out += output_files[i] + " and "+ de_facto_output_files[i] + ". tests are the same.\n"
            else: out += output_files[i] + " and "+ de_facto_output_files[i] + ". tests are not the same.\n"

    with open('test_results.txt', 'w') as f:
        f.write(out)
    print("The program finised compareing the test results, you can find them in 'test_results.txt' file.\nPress enter to close.")

main()












